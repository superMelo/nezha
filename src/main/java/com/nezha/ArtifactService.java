package com.nezha;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

/**
 * Artifact Service - auto-generate session summaries/artifacts.
 * Mirrors OpenClaw's task artifact system.
 */
@Service
public class ArtifactService {

    private final JdbcTemplate jdbc;

    public ArtifactService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Map<String, Object> createArtifact(final Long sessionId, final String title,
                                               final String content, final String artifactType) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(new org.springframework.jdbc.core.PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(java.sql.Connection con) throws java.sql.SQLException {
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO session_artifact (session_id, title, content, artifact_type, created_at) "
                                + "VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, sessionId);
                ps.setString(2, title);
                ps.setString(3, content);
                ps.setString(4, artifactType);
                return ps;
            }
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        Map<String, Object> artifact = new HashMap<String, Object>();
        artifact.put("id", id);
        artifact.put("sessionId", sessionId);
        artifact.put("title", title);
        artifact.put("content", content);
        artifact.put("artifactType", artifactType);
        return artifact;
    }

    public List<Map<String, Object>> listArtifacts(Long sessionId) {
        List<Map<String, Object>> rows = jdbc.queryForList(
                "SELECT id, session_id, title, content, artifact_type, created_at "
                        + "FROM session_artifact WHERE session_id = ? ORDER BY created_at DESC", sessionId);

        List<Map<String, Object>> artifacts = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> row : rows) {
            Map<String, Object> a = new HashMap<String, Object>();
            a.put("id", row.get("ID"));
            a.put("sessionId", row.get("SESSION_ID"));
            a.put("title", row.get("TITLE"));
            a.put("content", row.get("CONTENT"));
            a.put("artifactType", row.get("ARTIFACT_TYPE"));
            a.put("createdAt", row.get("CREATED_AT"));
            artifacts.add(a);
        }
        return artifacts;
    }

    public void deleteArtifact(Long id) {
        jdbc.update("DELETE FROM session_artifact WHERE id = ?", id);
    }

    /**
     * Auto-generate a summary artifact from session messages.
     */
    public Map<String, Object> generateSessionSummary(Long sessionId, String agentName) {
        List<Map<String, Object>> msgs = jdbc.queryForList(
                "SELECT role, content, agent_name, created_at FROM chat_message "
                        + "WHERE session_id = ? ORDER BY created_at ASC", sessionId);

        if (msgs.size() < 2) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("# Session Summary\n\n");
        sb.append("**Agent**: ").append(agentName != null ? agentName : "Unknown").append("\n");
        sb.append("**Messages**: ").append(msgs.size()).append("\n\n");

        sb.append("## Conversation\n\n");
        for (Map<String, Object> msg : msgs) {
            String role = (String) msg.get("ROLE");
            String content = (String) msg.get("CONTENT");
            if (content == null || content.isEmpty()) continue;

            if ("user".equals(role)) {
                sb.append("**User**: ").append(truncate(content, 300)).append("\n\n");
            } else {
                sb.append("**").append(msg.get("AGENT_NAME") != null ? msg.get("AGENT_NAME") : "Agent")
                        .append("**: ").append(truncate(content, 500)).append("\n\n");
            }
        }

        String title = "Session " + sessionId + " Summary";
        return createArtifact(sessionId, title, sb.toString(), "summary");
    }

    private String truncate(String s, int max) {
        if (s == null) return "";
        return s.length() <= max ? s : s.substring(0, max) + "...";
    }
}
