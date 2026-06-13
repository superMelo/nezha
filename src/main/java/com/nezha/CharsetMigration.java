package com.nezha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * Fixes MySQL charset from latin1 to utf8mb4 on startup.
 * Uses the VARBINARY trick to fix double-encoded data in existing rows.
 */
@Component
public class CharsetMigration {

    @Autowired
    private JdbcTemplate jdbc;

    @PostConstruct
    public void migrate() {
        try {
            jdbc.execute("SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci");
            fixTable("chat_session", new String[]{"title", "agent_name", "pipeline_name"});
            fixTable("chat_message", new String[]{"role", "content", "agent_name"});
            fixTable("agent_config", new String[]{"name", "system_prompt"});
            fixTable("pipeline_config", new String[]{"name", "description", "config_json"});
            fixTable("agent_memory", new String[]{"agent_name", "content"});
            fixTable("app_setting", new String[]{"setting_value"});
            fixTable("scheduled_task", new String[]{"task_name", "task_prompt", "agent_name"});
            jdbc.execute("ALTER DATABASE nezha CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");
            System.out.println("[CharsetMigration] Database charset migration completed successfully.");
        } catch (Exception e) {
            System.err.println("[CharsetMigration] Migration failed (may already be utf8mb4): " + e.getMessage());
        }
    }

    private void fixTable(String table, String[] textCols) {
        // Check if table has already been migrated (check a column's charset)
        try {
            List<Map<String, Object>> cols = jdbc.queryForList(
                "SELECT CHARACTER_SET_NAME FROM information_schema.COLUMNS " +
                "WHERE TABLE_SCHEMA = 'nezha' AND TABLE_NAME = ? AND COLUMN_NAME = ? " +
                "AND CHARACTER_SET_NAME != 'utf8mb4' LIMIT 1", table, textCols[0]);
            if (cols.isEmpty()) {
                // Already utf8mb4, skip
                return;
            }
        } catch (Exception e) {
            // Table might not exist, skip
            return;
        }

        for (String col : textCols) {
            try {
                // Get the column type
                List<Map<String, Object>> info = jdbc.queryForList(
                    "SELECT COLUMN_TYPE, IS_NULLABLE, COLUMN_DEFAULT FROM information_schema.COLUMNS " +
                    "WHERE TABLE_SCHEMA = 'nezha' AND TABLE_NAME = ? AND COLUMN_NAME = ?",
                    table, col);
                if (info.isEmpty()) continue;

                String colType = (String) info.get(0).get("COLUMN_TYPE");
                String nullable = (String) info.get(0).get("IS_NULLABLE");
                String defaultVal = (String) info.get(0).get("COLUMN_DEFAULT");

                // Determine the max size for VARBINARY conversion
                String varBinaryType = "VARBINARY(8192)";
                if (colType.toUpperCase().contains("VARCHAR")) {
                    // Extract the size, e.g., VARCHAR(255) -> VARBINARY(255)
                    varBinaryType = colType.replaceAll("(?i)varchar", "VARBINARY");
                }

                // Step 1: Convert to VARBINARY (preserves raw bytes)
                jdbc.execute("ALTER TABLE " + table + " MODIFY " + col + " " + varBinaryType +
                    (nullable.equals("YES") ? " NULL" : " NOT NULL"));

                // Step 2: Convert back to original type with utf8mb4
                String newType = colType + " CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci";
                jdbc.execute("ALTER TABLE " + table + " MODIFY " + col + " " + newType +
                    (nullable.equals("YES") ? " NULL" : " NOT NULL"));

                System.out.println("[CharsetMigration] Fixed " + table + "." + col);
            } catch (Exception e) {
                // Column might not exist or already fixed, continue
                System.err.println("[CharsetMigration] Skipping " + table + "." + col + ": " + e.getMessage());
            }
        }
    }
}
