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
public class ChatService {

    private final JdbcTemplate jdbc;

    public ChatService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Map<String, Object> createSession(final String title, final String agentName, final String modelName, final String pipelineName) {
        String actualTitle = (title == null || title.trim().isEmpty()) ? "New Chat" : title;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(new org.springframework.jdbc.core.PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(java.sql.Connection con) throws java.sql.SQLException {
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO chat_session (title, agent_name, pipeline_name, model_name) VALUES (?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, actualTitle);
                ps.setString(2, agentName);
                ps.setString(3, pipelineName);
                ps.setString(4, modelName);
                return ps;
            }
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        Map<String, Object> session = new HashMap<String, Object>();
        session.put("id", id);
        session.put("title", actualTitle);
        session.put("agentName", agentName);
        session.put("pipelineName", pipelineName);
        session.put("modelName", modelName);
        return session;
    }

    public List<Map<String, Object>> listSessions() {
        List<Map<String, Object>> rows = jdbc.queryForList(
                "SELECT cs.id, cs.title, cs.agent_name, cs.pipeline_name, cs.model_name, cs.created_at, cs.updated_at, "
                        + "(SELECT LEFT(cm.content, 80) FROM chat_message cm WHERE cm.session_id = cs.id AND cm.role = 'user' ORDER BY cm.id DESC LIMIT 1) AS last_message "
                        + "FROM chat_session cs ORDER BY cs.updated_at DESC");
        List<Map<String, Object>> sessions = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> row : rows) {
            Map<String, Object> session = new HashMap<String, Object>();
            session.put("id", row.get("ID"));
            session.put("title", row.get("TITLE"));
            session.put("agentName", row.get("AGENT_NAME"));
            session.put("pipelineName", row.get("PIPELINE_NAME"));
            session.put("modelName", row.get("MODEL_NAME"));
            session.put("createdAt", row.get("CREATED_AT"));
            session.put("updatedAt", row.get("UPDATED_AT"));
            session.put("lastMessage", row.get("LAST_MESSAGE"));
            sessions.add(session);
        }
        return sessions;
    }

    public void deleteSession(Long sessionId) {
        jdbc.update("DELETE FROM chat_message WHERE session_id = ?", sessionId);
        jdbc.update("DELETE FROM chat_session WHERE id = ?", sessionId);
    }

    public void saveMessage(Long sessionId, Msg msg) {
        jdbc.update(
                "INSERT INTO chat_message (session_id, role, content, agent_name, elapsed_ms, tool_calls) "
                        + "VALUES (?, ?, ?, ?, ?, ?)",
                sessionId, msg.getRole(), msg.getContent(),
                msg.getAgentName(), msg.getElapsedMs(), msg.getToolCalls());
        jdbc.update("UPDATE chat_session SET updated_at = CURRENT_TIMESTAMP WHERE id = ?", sessionId);
    }

    public List<Msg> getMessages(Long sessionId) {
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

    public void updateSessionTitle(Long sessionId, String title) {
        jdbc.update("UPDATE chat_session SET title = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?",
                title, sessionId);
    }

    public int getMessageCount(Long sessionId) {
        List<Map<String, Object>> rows = jdbc.queryForList(
                "SELECT COUNT(*) AS cnt FROM chat_message WHERE session_id = ?", sessionId);
        if (!rows.isEmpty()) {
            return ((Number) rows.get(0).get("CNT")).intValue();
        }
        return 0;
    }

    public String getSessionAgentName(Long sessionId) {
        List<Map<String, Object>> rows = jdbc.queryForList(
                "SELECT agent_name FROM chat_session WHERE id = ?", sessionId);
        if (!rows.isEmpty()) {
            Object name = rows.get(0).get("AGENT_NAME");
            return name != null ? name.toString() : "Assistant";
        }
        return "Assistant";
    }
}
