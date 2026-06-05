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
public class TaskService {

    private final JdbcTemplate jdbc;

    public TaskService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Long createTask(final String agentName, final String taskName, final String cronExpression,
                           final String taskPrompt, final Boolean enabled) {
        final boolean en = (enabled == null) ? true : enabled;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(new org.springframework.jdbc.core.PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(java.sql.Connection con) throws java.sql.SQLException {
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO scheduled_task (agent_name, task_name, cron_expression, task_prompt, enabled) "
                                + "VALUES (?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, agentName);
                ps.setString(2, taskName);
                ps.setString(3, cronExpression);
                ps.setString(4, taskPrompt);
                ps.setBoolean(5, en);
                return ps;
            }
        }, keyHolder);
        return ((Number)keyHolder.getKeys().get("ID")).longValue();
    }

    public void deleteTask(Long id) {
        jdbc.update("DELETE FROM scheduled_task WHERE id = ?", id);
    }

    public void toggleTask(Long id) {
        jdbc.update(
                "UPDATE scheduled_task SET enabled = CASE WHEN enabled = TRUE THEN FALSE ELSE TRUE END, "
                        + "updated_at = CURRENT_TIMESTAMP WHERE id = ?", id);
    }

    public List<Map<String, Object>> listTasks() {
        List<Map<String, Object>> rows = jdbc.queryForList(
                "SELECT id, agent_name, task_name, cron_expression, task_prompt, "
                        + "enabled, last_run_at, created_at, updated_at "
                        + "FROM scheduled_task ORDER BY created_at DESC");

        List<Map<String, Object>> tasks = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> row : rows) {
            Map<String, Object> task = new HashMap<String, Object>();
            task.put("id", row.get("ID"));
            task.put("agentName", row.get("AGENT_NAME"));
            task.put("taskName", row.get("TASK_NAME"));
            task.put("cronExpression", row.get("CRON_EXPRESSION"));
            task.put("taskPrompt", row.get("TASK_PROMPT"));
            task.put("enabled", row.get("ENABLED"));
            task.put("lastRunAt", row.get("LAST_RUN_AT"));
            task.put("createdAt", row.get("CREATED_AT"));
            task.put("updatedAt", row.get("UPDATED_AT"));
            tasks.add(task);
        }
        return tasks;
    }
}

