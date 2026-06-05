package com.nezha;

import java.util.HashMap;
import java.util.Map;

/**
 * Execution context passed through a pipeline.
 * Carries the current message, shared state, and references to services.
 */
public class PipelineContext {

    private String currentMessage;
    private String currentAgentName;
    private long sessionId;
    private Map<String, Object> variables;

    public PipelineContext(String message, long sessionId) {
        this.currentMessage = message;
        this.sessionId = sessionId;
        this.variables = new HashMap<String, Object>();
    }

    public String getCurrentMessage() { return currentMessage; }
    public void setCurrentMessage(String message) { this.currentMessage = message; }

    public String getCurrentAgentName() { return currentAgentName; }
    public void setCurrentAgentName(String agentName) { this.currentAgentName = agentName; }

    public long getSessionId() { return sessionId; }
    public void setSessionId(long sessionId) { this.sessionId = sessionId; }

    public Map<String, Object> getVariables() { return variables; }

    public void setVariable(String key, Object value) {
        variables.put(key, value);
    }

    public Object getVariable(String key) {
        return variables.get(key);
    }
}
