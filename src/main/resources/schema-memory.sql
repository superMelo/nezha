CREATE TABLE IF NOT EXISTS agent_memory (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    agent_name VARCHAR(128) NOT NULL,
    content TEXT NOT NULL,
    category VARCHAR(128),
    importance INT DEFAULT 5,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS pipeline_config (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(128) NOT NULL UNIQUE,
    type VARCHAR(50) NOT NULL,
    description VARCHAR(500),
    config_json TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS scheduled_task (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    agent_name VARCHAR(128) NOT NULL,
    task_name VARCHAR(255) NOT NULL,
    cron_expression VARCHAR(128) NOT NULL,
    task_prompt TEXT,
    enabled BOOLEAN DEFAULT TRUE,
    last_run_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS session_artifact (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    artifact_type VARCHAR(64) DEFAULT 'summary',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (session_id) REFERENCES chat_session(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS persona_template (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(128) NOT NULL UNIQUE,
    display_name VARCHAR(128),
    system_prompt TEXT,
    is_builtin BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS session_file (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_id BIGINT NOT NULL,
    original_name VARCHAR(512) NOT NULL,
    stored_name VARCHAR(512) NOT NULL,
    file_size BIGINT DEFAULT 0,
    content_type VARCHAR(256),
    text_content TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (session_id) REFERENCES chat_session(id) ON DELETE CASCADE
);
