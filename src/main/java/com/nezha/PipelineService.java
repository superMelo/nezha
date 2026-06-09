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
        return ((Number)keyHolder.getKeys().get("ID")).longValue();
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

        // Parse agent names from JSON
        List<String> agentNames = parseAgentNames(configJson);

        // Build context with REAL BaseAgent instances
        PipelineContext ctx = new PipelineContext(message, sessionId);
        for (String agentName : agentNames) {
            BaseAgent agent = agentService.getAgent(agentName);
            if (agent != null) {
                ctx.setVariable("agent." + agentName, agent);
            } else {
                // Store name as fallback so pipeline can show a useful error
                ctx.setVariable("agent." + agentName, agentName);
            }
        }

        // Build and execute pipeline
        Pipeline pipeline = buildPipeline(pipelineName, type, agentNames);
        List<Msg> results = pipeline.execute(message, ctx);

        return results;
    }

    private Pipeline buildPipeline(String name, String type, List<String> agentNames) {
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
        } else if ("loop".equals(type)) {
            LoopPipeline p = new LoopPipeline(name, "");
            if (!agentNames.isEmpty()) {
                p.setAgentName(agentNames.get(0));
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
     * Simple JSON parser for {"agents":["A","B"]} format.
     */
    @SuppressWarnings("unchecked")
    private List<String> parseAgentNames(String json) {
        List<String> names = new ArrayList<String>();
        if (json == null || json.isEmpty()) {
            return names;
        }
        try {
            // Use Jackson if available, otherwise simple string parsing
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            Map<String, Object> map = mapper.readValue(json, Map.class);
            Object agentsObj = map.get("agents");
            if (agentsObj instanceof List) {
                for (Object o : (List<?>) agentsObj) {
                    names.add(o.toString());
                }
            }
        } catch (Exception e) {
            // Fallback: try simple regex extraction
            java.util.regex.Pattern p = java.util.regex.Pattern.compile("\"([^\"]+)\"");
            java.util.regex.Matcher m = p.matcher(json);
            while (m.find()) {
                names.add(m.group(1));
            }
        }
        return names;
    }
}

