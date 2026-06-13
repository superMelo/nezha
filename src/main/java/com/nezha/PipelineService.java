package com.nezha;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages pipeline configurations and execution.
 */
@Service
public class PipelineService {

    private final JdbcTemplate jdbc;
    private final AgentService agentService;
    private final ChatService chatService;

    private final List<Pipeline> pipelines = new ArrayList<Pipeline>();

    public PipelineService(JdbcTemplate jdbc, AgentService agentService, ChatService chatService) {
        this.jdbc = jdbc;
        this.agentService = agentService;
        this.chatService = chatService;
        initDefaultPipelines();
    }

    private void initDefaultPipelines() {
        // Default: single agent pipeline
        String sql = "INSERT INTO pipeline_config (name, type, description, config_json) VALUES(?, ?, ?, ?) ON DUPLICATE KEY UPDATE type=VALUES(type), description=VALUES(description), config_json=VALUES(config_json)";
        jdbc.update(sql, "single", "sequential",
                "Single agent - direct conversation",
                "{\"agents\":[\"Assistant\"]}");
        jdbc.update(sql, "code-review", "sequential",
                "Code review: Coder writes code, then Reviewer reviews it",
                "{\"agents\":[\"Coder\",\"Reviewer\"]}");
        jdbc.update(sql, "broadcast", "broadcast",
                "Broadcast: sends message to all agents",
                "{\"agents\":[\"Assistant\",\"Coder\",\"Reviewer\"]}");
    }

    public List<Map<String, Object>> listPipelines() {
        List<Map<String, Object>> rows = jdbc.queryForList(
                "SELECT id, name, type, description, config_json, created_at "
                        + "FROM pipeline_config ORDER BY name");

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> row : rows) {
            Map<String, Object> p = new HashMap<String, Object>();
            p.put("id", row.get("ID"));
            p.put("name", row.get("NAME"));
            p.put("type", row.get("TYPE"));
            p.put("description", row.get("DESCRIPTION"));
            p.put("configJson", row.get("CONFIG_JSON"));
            p.put("createdAt", row.get("CREATED_AT"));
            result.add(p);
        }
        return result;
    }

    public Long createPipeline(final String name, final String type, final String description, final String configJson) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(new org.springframework.jdbc.core.PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(java.sql.Connection con) throws java.sql.SQLException {
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO pipeline_config (name, type, description, config_json) VALUES (?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, name);
                ps.setString(2, type);
                ps.setString(3, description);
                ps.setString(4, configJson);
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public void deletePipeline(Long id) {
        jdbc.update("DELETE FROM pipeline_config WHERE id = ?", id);
    }

    /**
     * Execute a pipeline by name.
     */
    public List<Msg> executePipeline(String pipelineName, long sessionId, String message) {
        // Load pipeline config
        List<Map<String, Object>> rows = jdbc.queryForList(
                "SELECT type, config_json FROM pipeline_config WHERE name = ?", pipelineName);

        if (rows.isEmpty()) {
            List<Msg> result = new ArrayList<Msg>();
            Msg errMsg = new Msg("system", "Pipeline not found: " + pipelineName);
            result.add(errMsg);
            return result;
        }

        Map<String, Object> row = rows.get(0);
        String type = (String) row.get("TYPE");
        String configJson = (String) row.get("CONFIG_JSON");

        // Parse full config from JSON
        Map<String, Object> config = parseConfig(configJson);

        // Parse agent names from JSON
        List<String> agentNames = configAgentNames(config);

        // Build context with REAL BaseAgent instances
        PipelineContext ctx = new PipelineContext(message, sessionId);
        for (String agentName : agentNames) {
            BaseAgent agent = agentService.getAgent(agentName);
            if (agent != null) {
                ctx.setVariable("agent." + agentName, agent);
            } else {
                ctx.setVariable("agent." + agentName, agentName);
            }
        }

        // Load full session history for pipelines that need shared context (groupchat)
        List<Msg> fullHistory = chatService.getMessages(sessionId);
        ctx.setVariable("fullHistory", fullHistory);

        // For ifelse: also resolve then/else branch agents
        if ("ifelse".equals(type) || "if-else".equals(type)) {
            List<String> thenAgents = configStringList(config, "thenAgents");
            List<String> elseAgents = configStringList(config, "elseAgents");
            for (String agentName : thenAgents) {
                BaseAgent agent = agentService.getAgent(agentName);
                if (agent != null) ctx.setVariable("agent." + agentName, agent);
                else ctx.setVariable("agent." + agentName, agentName);
            }
            for (String agentName : elseAgents) {
                BaseAgent agent = agentService.getAgent(agentName);
                if (agent != null) ctx.setVariable("agent." + agentName, agent);
                else ctx.setVariable("agent." + agentName, agentName);
            }
        }

        // Build and execute pipeline
        Pipeline pipeline = buildPipeline(pipelineName, type, agentNames, config);
        List<Msg> results = pipeline.execute(message, ctx);

        return results;
    }

    private Pipeline buildPipeline(String name, String type, List<String> agentNames, Map<String, Object> config) {
        if ("sequential".equals(type)) {
            SequentialPipeline p = new SequentialPipeline(name, "");
            for (String agent : agentNames) {
                p.addAgent(agent);
            }
            return p;
        } else if ("broadcast".equals(type)) {
            BroadcastPipeline p = new BroadcastPipeline(name, "");
            for (String agent : agentNames) {
                p.addAgent(agent);
            }
            return p;
        } else if ("groupchat".equals(type) || "group".equals(type)) {
            GroupChatPipeline p = new GroupChatPipeline(name, "");
            for (String agent : agentNames) {
                p.addAgent(agent);
            }
            return p;
        } else if ("loop".equals(type)) {
            LoopPipeline p = new LoopPipeline(name, "");
            if (!agentNames.isEmpty()) {
                p.setAgentName(agentNames.get(0));
            }
            // Read type-specific config
            if (config.containsKey("maxIterations")) {
                p.setMaxIterations(configInt(config, "maxIterations", 20));
            }
            if (config.containsKey("exitCondition")) {
                p.setExitCondition(configString(config, "exitCondition", "done"));
            }
            return p;
        } else if ("ifelse".equals(type) || "if-else".equals(type)) {
            IfElsePipeline p = new IfElsePipeline(name, "");
            p.setConditionVariable(configString(config, "conditionVariable", "route.condition"));

            // Build then-branch sub-pipeline
            List<String> thenAgents = configStringList(config, "thenAgents");
            if (!thenAgents.isEmpty()) {
                SequentialPipeline thenPipe = new SequentialPipeline(name + "-then", "");
                for (String agent : thenAgents) {
                    thenPipe.addAgent(agent);
                }
                p.setThenPipeline(thenPipe);
            }

            // Build else-branch sub-pipeline
            List<String> elseAgents = configStringList(config, "elseAgents");
            if (!elseAgents.isEmpty()) {
                SequentialPipeline elsePipe = new SequentialPipeline(name + "-else", "");
                for (String agent : elseAgents) {
                    elsePipe.addAgent(agent);
                }
                p.setElsePipeline(elsePipe);
            }
            return p;
        } else {
            // Default to sequential
            SequentialPipeline p = new SequentialPipeline(name, "");
            for (String agent : agentNames) {
                p.addAgent(agent);
            }
            return p;
        }
    }

    /**
     * Parse config JSON into a Map, or return empty map on failure.
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> parseConfig(String json) {
        if (json == null || json.isEmpty()) {
            return new java.util.HashMap<String, Object>();
        }
        try {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            return mapper.readValue(json, Map.class);
        } catch (Exception e) {
            return new java.util.HashMap<String, Object>();
        }
    }

    /** Extract agent names list from parsed config. */
    @SuppressWarnings("unchecked")
    private List<String> configAgentNames(Map<String, Object> config) {
        List<String> names = new ArrayList<String>();
        Object agentsObj = config.get("agents");
        if (agentsObj instanceof List) {
            for (Object o : (List<?>) agentsObj) {
                names.add(o.toString());
            }
        }
        return names;
    }

    /** Read an integer from config with a default. */
    private int configInt(Map<String, Object> config, String key, int defaultValue) {
        Object val = config.get(key);
        if (val instanceof Number) {
            return ((Number) val).intValue();
        }
        if (val instanceof String) {
            try { return Integer.parseInt((String) val); } catch (NumberFormatException e) { }
        }
        return defaultValue;
    }

    /** Read a string from config with a default. */
    private String configString(Map<String, Object> config, String key, String defaultValue) {
        Object val = config.get(key);
        if (val instanceof String && !((String) val).isEmpty()) {
            return (String) val;
        }
        return defaultValue;
    }

    /** Read a string list from config. */
    @SuppressWarnings("unchecked")
    private List<String> configStringList(Map<String, Object> config, String key) {
        List<String> result = new ArrayList<String>();
        Object val = config.get(key);
        if (val instanceof List) {
            for (Object o : (List<?>) val) {
                result.add(o.toString());
            }
        }
        return result;
    }
}

