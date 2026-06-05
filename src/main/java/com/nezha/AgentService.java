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

    private final List<BaseAgent> customAgents = new ArrayList<BaseAgent>();

    public AgentService(JdbcTemplate jdbc, ModelFactory modelFactory, ToolRegistry toolRegistry) {
        this.jdbc = jdbc;
        this.modelFactory = modelFactory;
        this.toolRegistry = toolRegistry;

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
        BaseAgent agent = new BaseAgent(modelFactory, toolRegistry);
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
        BaseAgent agent = new BaseAgent(modelFactory, toolRegistry);
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
        // Check built-in agents first
        if (assistantAgent.getName().equals(name)) {
            return assistantAgent;
        }
        if (coderAgent.getName().equals(name)) {
            return coderAgent;
        }
        if (reviewerAgent.getName().equals(name)) {
            return reviewerAgent;
        }

        // Check custom agents (in-memory)
        for (BaseAgent agent : customAgents) {
            if (agent.getName().equals(name)) {
                return agent;
            }
        }

        // Load from database and create instance on-the-fly
        List<Map<String, Object>> rows = jdbc.queryForList(
                "SELECT system_prompt, model_name, memory_size FROM agent_config WHERE name = ?", name);
        if (!rows.isEmpty()) {
            Map<String, Object> row = rows.get(0);
            String sysPrompt = row.get("SYSTEM_PROMPT") != null ? row.get("SYSTEM_PROMPT").toString() : "You are a helpful assistant.";
            String modelName = row.get("MODEL_NAME") != null ? row.get("MODEL_NAME").toString() : "deepseek";
            int memSize = row.get("MEMORY_SIZE") instanceof Number ? ((Number)row.get("MEMORY_SIZE")).intValue() : 50;

            // Create a real BaseAgent with proper ModelFactory and ToolRegistry
            BaseAgent agent = new BaseAgent(modelFactory, toolRegistry);
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
