package com.nezha;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AgentService {

    private final JdbcTemplate jdbc;
    private final ModelFactory modelFactory;
    private final ToolRegistry toolRegistry;
    private final BaseAgent assistantAgent;
    private final BaseAgent coderAgent;
    private final BaseAgent reviewerAgent;

    private final ModelRouter modelRouter;

    private final List<BaseAgent> customAgents = new java.util.concurrent.CopyOnWriteArrayList<BaseAgent>();

    public AgentService(JdbcTemplate jdbc, ModelFactory modelFactory, ToolRegistry toolRegistry, ModelRouter modelRouter) {
        this.jdbc = jdbc;
        this.modelFactory = modelFactory;
        this.toolRegistry = toolRegistry;
        this.modelRouter = modelRouter;

        // Initialize built-in agents
        this.assistantAgent = createAgent("Assistant",
                "You are a helpful assistant. Answer user questions clearly and concisely. "
                        + "Use tools when appropriate to help the user.",
                "deepseek", 50, modelFactory, toolRegistry);

        this.coderAgent = createAgent("Coder",
                "You are an expert programmer. Write clean, efficient, and well-documented code. "
                        + "Always explain your approach before writing code.",
                "deepseek", 100, modelFactory, toolRegistry);

        this.reviewerAgent = createAgent("Reviewer",
                "You are a code reviewer. Analyze code for bugs, performance issues, "
                        + "security vulnerabilities, and style improvements. "
                        + "Provide constructive feedback with specific suggestions.",
                "deepseek", 100, modelFactory, toolRegistry);

        initDefaultAgents();
    }

    private BaseAgent createAgent(String name, String sysPrompt, String modelName,
                                   int memorySize, ModelFactory modelFactory, ToolRegistry toolRegistry) {
        BaseAgent agent = new BaseAgent(modelFactory, toolRegistry, modelRouter);
        agent.setName(name);
        agent.setSysPrompt(sysPrompt);
        agent.setModelName(modelName);
        agent.setMemorySize(memorySize);
        return agent;
    }

    private void initDefaultAgents() {
        String sql = "INSERT INTO agent_config (name, system_prompt, model_name, memory_size, is_custom) VALUES(?, ?, ?, ?, FALSE) ON DUPLICATE KEY UPDATE system_prompt=VALUES(system_prompt), model_name=VALUES(model_name), memory_size=VALUES(memory_size)";
        jdbc.update(sql,
                assistantAgent.getName(), assistantAgent.getSysPrompt(),
                assistantAgent.getModelName(), assistantAgent.getMemorySize());
        jdbc.update(sql,
                coderAgent.getName(), coderAgent.getSysPrompt(),
                coderAgent.getModelName(), coderAgent.getMemorySize());
        jdbc.update(sql,
                reviewerAgent.getName(), reviewerAgent.getSysPrompt(),
                reviewerAgent.getModelName(), reviewerAgent.getMemorySize());
    }

    public List<Map<String, Object>> listAgents() {
        List<Map<String, Object>> agents = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> rows = jdbc.queryForList(
                "SELECT id, name, memory_size, model_name, is_custom FROM agent_config ORDER BY name");

        for (Map<String, Object> row : rows) {
            Map<String, Object> agent = new HashMap<String, Object>();
            agent.put("id", row.get("ID"));
            agent.put("name", row.get("NAME"));
            agent.put("memorySize", row.get("MEMORY_SIZE"));
            agent.put("modelName", row.get("MODEL_NAME"));
            agent.put("isCustom", row.get("IS_CUSTOM"));
            agents.add(agent);
        }
        return agents;
    }

    public BaseAgent createCustomAgent(String name, String sysPrompt, String modelName, int memorySize) {
        // Auto-inject agent identity: ensure agent knows its EXACT full name
        String identityPrompt = "Your exact name is \"" + name + "\". "
                + "You must always call yourself \"" + name + "\" — never shorten, abbreviate, or change it. "
                + "The name \"" + name + "\" is your complete identity. ";
        String fullPrompt = identityPrompt + (sysPrompt != null ? sysPrompt : "You are a helpful assistant.");

        BaseAgent agent = new BaseAgent(modelFactory, toolRegistry, modelRouter);
        agent.setName(name);
        agent.setSysPrompt(fullPrompt);
        agent.setModelName(modelName);
        agent.setMemorySize(memorySize);

        jdbc.update(
                "INSERT INTO agent_config (name, system_prompt, model_name, memory_size, is_custom) VALUES(?, ?, ?, ?, TRUE) ON DUPLICATE KEY UPDATE system_prompt=VALUES(system_prompt), model_name=VALUES(model_name), memory_size=VALUES(memory_size)",
                name, fullPrompt, modelName, memorySize);

        customAgents.add(agent);
        return agent;
    }

    public BaseAgent getAgent(String name) {
        // Always refresh system_prompt from DB
        List<Map<String, Object>> rows = jdbc.queryForList(
                "SELECT system_prompt, model_name, memory_size FROM agent_config WHERE name = ?", name);
        String sysPrompt = "You are a helpful assistant.";
        String modelName = "deepseek";
        int memSize = 50;
        if (!rows.isEmpty()) {
            Map<String, Object> row = rows.get(0);
            if (row.get("SYSTEM_PROMPT") != null) sysPrompt = row.get("SYSTEM_PROMPT").toString();
            if (row.get("MODEL_NAME") != null) modelName = row.get("MODEL_NAME").toString();
            if (row.get("MEMORY_SIZE") instanceof Number) memSize = ((Number)row.get("MEMORY_SIZE")).intValue();
        }

        // Check built-in agents
        if ("Assistant".equals(name) && assistantAgent != null) {
            assistantAgent.setSysPrompt(sysPrompt);
            assistantAgent.setModelName(modelName);
            return assistantAgent;
        }
        if ("Coder".equals(name) && coderAgent != null) {
            coderAgent.setSysPrompt(sysPrompt);
            coderAgent.setModelName(modelName);
            return coderAgent;
        }
        if ("Reviewer".equals(name) && reviewerAgent != null) {
            reviewerAgent.setSysPrompt(sysPrompt);
            reviewerAgent.setModelName(modelName);
            return reviewerAgent;
        }

        // Check custom agents (in-memory)
        for (BaseAgent agent : customAgents) {
            if (agent.getName().equals(name)) {
                agent.setSysPrompt(sysPrompt);
                agent.setModelName(modelName);
                return agent;
            }
        }

        // Create new instance from database
        if (!rows.isEmpty()) {
            BaseAgent agent = new BaseAgent(modelFactory, toolRegistry, modelRouter);
            agent.setName(name);
            agent.setSysPrompt(sysPrompt);
            agent.setModelName(modelName);
            agent.setMemorySize(memSize);
            customAgents.add(agent);
            return agent;
        }

        return null;
    }

    public String getAgentSysPrompt(String name) {
        List<Map<String, Object>> rows = jdbc.queryForList(
                "SELECT system_prompt FROM agent_config WHERE name = ?", name);
        if (!rows.isEmpty()) {
            Object prompt = rows.get(0).get("SYSTEM_PROMPT");
            return prompt != null ? prompt.toString() : null;
        }
        return null;
    }

    public boolean deleteAgent(String name) {
        // Don't delete built-in agents
        if ("Assistant".equals(name) || "Coder".equals(name) || "Reviewer".equals(name)) {
            return false;
        }
        // Remove from custom agents list
        customAgents.removeIf(a -> a.getName().equals(name));
        // Remove from database - check if a row was actually deleted
        int rows = jdbc.update("DELETE FROM agent_config WHERE name = ? AND is_custom = TRUE", name);
        return rows > 0;
    }

    public boolean deleteAgentById(Long id) {
        // Don't delete built-in agents (id <= 3)
        Map<String, Object> row = null;
        List<Map<String, Object>> rows = jdbc.queryForList(
                "SELECT name, is_custom FROM agent_config WHERE id = ?", id);
        if (!rows.isEmpty()) row = rows.get(0);
        if (row == null) return false;
        String name = (String) row.get("NAME");
        Boolean isCustom = (Boolean) row.get("IS_CUSTOM");
        if (isCustom == null || !isCustom) return false;

        // Remove from custom agents list
        customAgents.removeIf(a -> a.getName().equals(name));
        // Remove from database
        int deleted = jdbc.update("DELETE FROM agent_config WHERE id = ? AND is_custom = TRUE", id);
        return deleted > 0;
    }

    public String getAgentModelName(String name) {
        List<Map<String, Object>> rows = jdbc.queryForList(
                "SELECT model_name FROM agent_config WHERE name = ?", name);
        if (!rows.isEmpty()) {
            Object model = rows.get(0).get("MODEL_NAME");
            return model != null ? model.toString() : null;
        }
        return null;
    }
}
