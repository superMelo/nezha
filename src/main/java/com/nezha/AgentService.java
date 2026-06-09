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

    private final List<BaseAgent> customAgents = new ArrayList<BaseAgent>();

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
        String sql = "MERGE INTO agent_config (name, system_prompt, model_name, memory_size, is_custom) "
                + "KEY(name) VALUES(?, ?, ?, ?, FALSE)";
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
                "SELECT name, memory_size, model_name, is_custom FROM agent_config ORDER BY name");

        for (Map<String, Object> row : rows) {
            Map<String, Object> agent = new HashMap<String, Object>();
            agent.put("name", row.get("NAME"));
            agent.put("memorySize", row.get("MEMORY_SIZE"));
            agent.put("modelName", row.get("MODEL_NAME"));
            agent.put("isCustom", row.get("IS_CUSTOM"));
            agents.add(agent);
        }
        return agents;
    }

    public BaseAgent createCustomAgent(String name, String sysPrompt, String modelName, int memorySize) {
        BaseAgent agent = new BaseAgent(modelFactory, toolRegistry, modelRouter);
        agent.setName(name);
        agent.setSysPrompt(sysPrompt);
        agent.setModelName(modelName);
        agent.setMemorySize(memorySize);

        jdbc.update(
                "MERGE INTO agent_config (name, system_prompt, model_name, memory_size, is_custom) "
                        + "KEY(name) VALUES(?, ?, ?, ?, TRUE)",
                name, sysPrompt, modelName, memorySize);

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
        // Remove from database
        jdbc.update("DELETE FROM agent_config WHERE name = ? AND is_custom = TRUE", name);
        return true;
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
