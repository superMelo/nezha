-- Fix MySQL charset: convert tables from latin1 to utf8mb4
-- Uses the VARBINARY intermediate approach to fix double-encoded data
-- Safe to run repeatedly (idempotent)

-- chat_session
ALTER TABLE chat_session MODIFY title VARBINARY(255);
ALTER TABLE chat_session MODIFY title VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE chat_session MODIFY agent_name VARBINARY(128);
ALTER TABLE chat_session MODIFY agent_name VARCHAR(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE chat_session MODIFY pipeline_name VARBINARY(128);
ALTER TABLE chat_session MODIFY pipeline_name VARCHAR(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- chat_message
ALTER TABLE chat_message MODIFY role VARBINARY(64);
ALTER TABLE chat_message MODIFY role VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE chat_message MODIFY content VARBINARY(8192);
ALTER TABLE chat_message MODIFY content TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE chat_message MODIFY agent_name VARBINARY(128);
ALTER TABLE chat_message MODIFY agent_name VARCHAR(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- agent_config
ALTER TABLE agent_config MODIFY name VARBINARY(128);
ALTER TABLE agent_config MODIFY name VARCHAR(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE agent_config MODIFY system_prompt VARBINARY(8192);
ALTER TABLE agent_config MODIFY system_prompt TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- pipeline_config
ALTER TABLE pipeline_config MODIFY name VARBINARY(128);
ALTER TABLE pipeline_config MODIFY name VARCHAR(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE pipeline_config MODIFY description VARBINARY(500);
ALTER TABLE pipeline_config MODIFY description VARCHAR(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE pipeline_config MODIFY config_json VARBINARY(8192);
ALTER TABLE pipeline_config MODIFY config_json TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- agent_memory
ALTER TABLE agent_memory MODIFY agent_name VARBINARY(128);
ALTER TABLE agent_memory MODIFY agent_name VARCHAR(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE agent_memory MODIFY content VARBINARY(8192);
ALTER TABLE agent_memory MODIFY content TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- app_setting
ALTER TABLE app_setting MODIFY setting_value VARBINARY(4096);
ALTER TABLE app_setting MODIFY setting_value VARCHAR(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- scheduled_task
ALTER TABLE scheduled_task MODIFY task_name VARBINARY(255);
ALTER TABLE scheduled_task MODIFY task_name VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE scheduled_task MODIFY task_prompt VARBINARY(8192);
ALTER TABLE scheduled_task MODIFY task_prompt TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE scheduled_task MODIFY agent_name VARBINARY(128);
ALTER TABLE scheduled_task MODIFY agent_name VARCHAR(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Database default
ALTER DATABASE nezha CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
