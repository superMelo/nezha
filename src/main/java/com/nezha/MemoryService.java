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

@Service
public class MemoryService {

    private final JdbcTemplate jdbc;

    public MemoryService(JdbcTemplate jdbc, ToolRegistry toolRegistry) {
        this.jdbc = jdbc;

        // Register built-in tool handlers
        toolRegistry.registerTool(
                new ToolRegistry.ToolDefinition("search_memory", "Search agent memory by keyword", "search_memory", "query"),
                args -> {
                    // args format: agentName, query
                    String[] parts = args.split(",", 2);
                    String agent = parts.length > 0 ? parts[0].trim() : "Assistant";
                    String query = parts.length > 1 ? parts[1].trim() : args.trim();
                    List<Map<String, Object>> results = searchMemories(agent, query);
                    if (results.isEmpty()) return "No memories found for query: " + query;
                    StringBuilder sb = new StringBuilder();
                    for (Map<String, Object> r : results) {
                        sb.append("- ").append(r.get("content")).append("\n");
                    }
                    return sb.toString();
                }
        );
        toolRegistry.registerTool(
                new ToolRegistry.ToolDefinition("save_memory", "Save important information to memory", "save_memory", "content, category"),
                args -> {
                    String[] parts = args.split(",", 2);
                    String content = parts.length > 0 ? parts[0].trim() : args.trim();
                    String category = parts.length > 1 ? parts[1].trim() : "auto";
                    addMemory("Assistant", content, category, 5);
                    return "Memory saved: " + content.substring(0, Math.min(50, content.length())) + "...";
                }
        );
    }

    public Long addMemory(final String agentName, final String content, final String category, final Integer importance) {
        int imp = (importance == null) ? 5 : importance;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(new org.springframework.jdbc.core.PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(java.sql.Connection con) throws java.sql.SQLException {
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO agent_memory (agent_name, content, category, importance) VALUES (?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, agentName);
                ps.setString(2, content);
                ps.setString(3, category);
                ps.setInt(4, imp);
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public void deleteMemory(Long id) {
        jdbc.update("DELETE FROM agent_memory WHERE id = ?", id);
    }

    public List<Map<String, Object>> getMemories(String agentName) {
        List<Map<String, Object>> rows = jdbc.queryForList(
                "SELECT id, agent_name, content, category, importance, created_at "
                        + "FROM agent_memory WHERE agent_name = ? ORDER BY created_at DESC", agentName);

        List<Map<String, Object>> memories = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> row : rows) {
            Map<String, Object> memory = new HashMap<String, Object>();
            memory.put("id", row.get("ID"));
            memory.put("agentName", row.get("AGENT_NAME"));
            memory.put("content", row.get("CONTENT"));
            memory.put("category", row.get("CATEGORY"));
            memory.put("importance", row.get("IMPORTANCE"));
            memory.put("createdAt", row.get("CREATED_AT"));
            memories.add(memory);
        }
        return memories;
    }

    public List<Map<String, Object>> searchMemories(String agentName, String query) {
        List<Map<String, Object>> rows = jdbc.queryForList(
                "SELECT id, agent_name, content, category, importance, created_at "
                        + "FROM agent_memory WHERE agent_name = ? AND content LIKE ? ORDER BY created_at DESC",
                agentName, "%" + query + "%");

        List<Map<String, Object>> memories = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> row : rows) {
            Map<String, Object> memory = new HashMap<String, Object>();
            memory.put("id", row.get("ID"));
            memory.put("agentName", row.get("AGENT_NAME"));
            memory.put("content", row.get("CONTENT"));
            memory.put("category", row.get("CATEGORY"));
            memory.put("importance", row.get("IMPORTANCE"));
            memory.put("createdAt", row.get("CREATED_AT"));
            memories.add(memory);
        }
        return memories;
    }

    public String formatMemoriesForPrompt(String agentName, int maxSize) {
        List<Map<String, Object>> memories = getMemories(agentName);
        if (memories.isEmpty()) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Relevant memories:\n");
        int count = 0;
        for (Map<String, Object> memory : memories) {
            if (count >= maxSize) {
                break;
            }
            sb.append("- ").append(memory.get("content"));
            Object cat = memory.get("category");
            if (cat != null) {
                sb.append(" [").append(cat).append("]");
            }
            sb.append("\n");
            count++;
        }
        return sb.toString();
    }
}
