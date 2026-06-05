package com.nezha;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ToolRegistry {

    private final Map<String, ToolDefinition> tools = new ConcurrentHashMap<String, ToolDefinition>();

    public ToolRegistry() {
        // Register built-in tools
        registerTool(new ToolDefinition(
                "search_memory",
                "Search agent memory by keyword",
                "search_memory",
                "query"
        ));
        registerTool(new ToolDefinition(
                "save_memory",
                "Save important information to memory",
                "save_memory",
                "content, category"
        ));
        registerTool(new ToolDefinition(
                "compress_context",
                "Compress conversation context to reduce token usage",
                "compress_context",
                "sessionId"
        ));
    }

    public void registerTool(ToolDefinition tool) {
        tools.put(tool.getName(), tool);
    }

    public void unregisterTool(String name) {
        tools.remove(name);
    }

    public ToolDefinition getTool(String name) {
        return tools.get(name);
    }

    public List<ToolDefinition> getAllTools() {
        return new ArrayList<ToolDefinition>(tools.values());
    }

    public String formatToolsPrompt() {
        StringBuilder sb = new StringBuilder();
        sb.append("Available tools:\n");
        for (ToolDefinition tool : tools.values()) {
            sb.append("- ").append(tool.getName()).append(": ").append(tool.getDescription());
            sb.append(" (parameters: ").append(tool.getParameters()).append(")\n");
        }
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
