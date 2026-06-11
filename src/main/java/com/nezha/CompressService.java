package com.nezha;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CompressService {

    private final JdbcTemplate jdbc;

    public CompressService(JdbcTemplate jdbc, ToolRegistry toolRegistry) {
        this.jdbc = jdbc;

        // Register built-in compress tool handler
        toolRegistry.registerTool(
                new ToolRegistry.ToolDefinition("compress_context", "Compress conversation context to reduce token usage", "compress_context", "sessionId"),
                args -> {
                    try {
                        Long sessionId = Long.parseLong(args.trim());
                        int removed = compressSession(sessionId);
                        return "Compressed " + removed + " messages from session " + sessionId;
                    } catch (NumberFormatException e) {
                        return "Error: invalid sessionId: " + args;
                    }
                }
        );
    }

    /**
     * Compress a session by keeping first and last messages, folding the middle.
     * Returns the number of messages removed.
     */
    public int compressSession(Long sessionId, int keepFirst, int keepLast) {
        List<Msg> messages = getMessages(sessionId);
        if (messages.size() <= keepFirst + keepLast) {
            return 0;
        }

        int total = messages.size();
        int middleStart = keepFirst;
        int middleEnd = total - keepLast;

        // Collect IDs of messages in the middle to remove
        List<Map<String, Object>> allRows = jdbc.queryForList(
                "SELECT id FROM chat_message WHERE session_id = ? ORDER BY id", sessionId);

        List<Long> idsToRemove = new ArrayList<Long>();
        for (int i = middleStart; i < middleEnd && i < allRows.size(); i++) {
            Long id = (Long) allRows.get(i).get("ID");
            idsToRemove.add(id);
        }

        // Remove middle messages
        for (Long id : idsToRemove) {
            jdbc.update("DELETE FROM chat_message WHERE id = ?", id);
        }

        // Add a summary message
        String summary = "[Context compressed: " + idsToRemove.size() + " messages folded]";
        jdbc.update(
                "INSERT INTO chat_message (session_id, role, content) VALUES (?, 'system', ?)",
                sessionId, summary);

        return idsToRemove.size();
    }

    /**
     * Compress with default settings: keep first 2 and last 4 messages.
     */
    public int compressSession(Long sessionId) {
        return compressSession(sessionId, 2, 4);
    }

    private List<Msg> getMessages(Long sessionId) {
        List<Map<String, Object>> rows = jdbc.queryForList(
                "SELECT role, content, agent_name, elapsed_ms, tool_calls, created_at "
                        + "FROM chat_message WHERE session_id = ? ORDER BY id", sessionId);

        List<Msg> messages = new ArrayList<Msg>();
        for (Map<String, Object> row : rows) {
            Msg msg = new Msg();
            msg.setRole((String) row.get("ROLE"));
            msg.setContent((String) row.get("CONTENT"));
            msg.setAgentName((String) row.get("AGENT_NAME"));
            Object elapsed = row.get("ELAPSED_MS");
            if (elapsed != null) {
                msg.setElapsedMs(((Number) elapsed).longValue());
            }
            msg.setToolCalls((String) row.get("TOOL_CALLS"));
            Object ts = row.get("CREATED_AT");
            if (ts != null) {
                msg.setTimestamp(((java.sql.Timestamp) ts).getTime());
            } else {
                msg.setTimestamp(System.currentTimeMillis());
            }
            messages.add(msg);
        }
        return messages;
    }
}
