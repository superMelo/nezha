package com.nezha;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ToolRegistry {

    private final Map<String, ToolDefinition> tools = new ConcurrentHashMap<String, ToolDefinition>();
    private final Map<String, ToolHandler> handlers = new ConcurrentHashMap<String, ToolHandler>();

    /**
     * Functional interface for tool execution handlers.
     */
    @FunctionalInterface
    public interface ToolHandler {
        String execute(String args);
    }

    public ToolRegistry() {
        // Tool definitions and handlers are registered by services that implement them.
        // See MemoryService and CompressService for built-in handler registration.
    }

    public void registerTool(ToolDefinition tool) {
        tools.put(tool.getName(), tool);
    }

    public void registerHandler(String toolName, ToolHandler handler) {
        handlers.put(toolName, handler);
    }

    public void registerTool(ToolDefinition tool, ToolHandler handler) {
        tools.put(tool.getName(), tool);
        handlers.put(tool.getName(), handler);
    }

    public void unregisterTool(String name) {
        tools.remove(name);
        handlers.remove(name);
    }

    public ToolDefinition getTool(String name) {
        return tools.get(name);
    }

    public List<ToolDefinition> getAllTools() {
        return new ArrayList<ToolDefinition>(tools.values());
    }

    /**
     * Execute a tool by name with the given arguments.
     * Returns the result string, or null if the tool doesn't exist or has no handler.
     */
    public String executeTool(String toolName, String args) {
        ToolHandler handler = handlers.get(toolName);
        if (handler == null) {
            return null;
        }
        try {
            return handler.execute(args);
        } catch (Exception e) {
            return "Tool error: " + e.getMessage();
        }
    }

    public boolean hasHandler(String toolName) {
        return handlers.containsKey(toolName);
    }

    public String formatToolsPrompt() {
        StringBuilder sb = new StringBuilder();
        sb.append("You have access to the following tools. To use a tool, respond with exactly:\n");
        sb.append("[TOOL_CALLS] tool_name(param1, param2, ...)\n\n");
        sb.append("Available tools:\n");
        for (ToolDefinition tool : tools.values()) {
            sb.append("- ").append(tool.getName());
            sb.append("(").append(tool.getParameters()).append(")");
            sb.append(": ").append(tool.getDescription()).append("\n");
        }
        sb.append("\nExample: [TOOL_CALLS] read_file(C:/path/to/file, 0, 100)\n");
        sb.append("After using a tool, wait for the tool result before responding.");
        return sb.toString();
    }

    public static class ToolDefinition {
        private String name;
        private String description;
        private String handler;
        private String parameters;

        public ToolDefinition() {
        }

        public ToolDefinition(String name, String description, String handler, String parameters) {
            this.name = name;
            this.description = description;
            this.handler = handler;
            this.parameters = parameters;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getHandler() {
            return handler;
        }

        public void setHandler(String handler) {
            this.handler = handler;
        }

        public String getParameters() {
            return parameters;
        }

        public void setParameters(String parameters) {
            this.parameters = parameters;
        }
    }
}
