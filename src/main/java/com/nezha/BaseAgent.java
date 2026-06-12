package com.nezha;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BaseAgent {

    private String name;
    private String sysPrompt;
    private String modelName;
    private int memorySize;

    private final ModelFactory modelFactory;
    private final ToolRegistry toolRegistry;
    private final ModelRouter modelRouter;

    public BaseAgent(ModelFactory modelFactory, ToolRegistry toolRegistry, ModelRouter modelRouter) {
        this.modelFactory = modelFactory;
        this.toolRegistry = toolRegistry;
        this.modelRouter = modelRouter;
        this.memorySize = 50;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSysPrompt() {
        return sysPrompt;
    }

    public void setSysPrompt(String sysPrompt) {
        this.sysPrompt = sysPrompt;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public int getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(int memorySize) {
        this.memorySize = memorySize;
    }

    public List<Msg> chat(List<Msg> history) {
        // Auto-route: find the last user message and route to best model
        String routedModel = this.modelName;
        for (int i = history.size() - 1; i >= 0; i--) {
            Msg m = history.get(i);
            if ("user".equals(m.getRole()) && m.getContent() != null && !m.getContent().isEmpty()) {
                routedModel = modelRouter.route(m.getContent());
                break;
            }
        }
        return chatWithTools(history, routedModel);
    }

    public List<Msg> chat(List<Msg> history, String overrideModel) {
        return chatWithTools(history, overrideModel);
    }

    /**
     * Internal method that implements the tool-calling loop.
     * If the model returns tool calls, executes them and re-invokes the model
     * with the tool results appended (up to 5 tool-calling rounds).
     */
    private List<Msg> chatWithTools(List<Msg> history, String modelNameToUse) {
        LLMModel model;
        if (modelNameToUse != null && !modelNameToUse.isEmpty()) {
            model = modelFactory.getModel(modelNameToUse);
        } else if (modelName != null && !modelName.isEmpty()) {
            model = modelFactory.getModel(modelName);
        } else {
            model = modelFactory.getDefaultModel();
        }

        String fullPrompt = buildFullPrompt();

        List<Msg> allReplies = new ArrayList<Msg>();
        List<Msg> currentHistory = new ArrayList<Msg>(history);
        int maxRounds = 5;

        for (int round = 0; round < maxRounds; round++) {
            long startTime = System.currentTimeMillis();
            List<Msg> result;
            try {
                result = model.chat(fullPrompt, currentHistory);
            } catch (Exception e) {
                long elapsed = System.currentTimeMillis() - startTime;
                Msg errorMsg = new Msg("assistant", "Error: " + e.getMessage());
                errorMsg.setAgentName(this.name);
                errorMsg.setElapsedMs(elapsed);
                allReplies.add(errorMsg);
                return allReplies;
            }
            long elapsed = System.currentTimeMillis() - startTime;

            for (Msg msg : result) {
                msg.setAgentName(this.name);
                msg.setElapsedMs(elapsed);
            }
            allReplies.addAll(result);

            // Check if the model wants to call tools
            boolean foundToolCall = false;
            for (Msg msg : result) {
                String content = msg.getContent();
                if (content != null && content.contains("[TOOL_CALLS]")) {
                    foundToolCall = true;
                    // Add assistant message to history
                    currentHistory.add(msg);

                    // Extract and execute tool calls
                    String toolResults = executeToolCalls(content);
                    if (toolResults != null && !toolResults.isEmpty()) {
                        // Use "system" role for tool results to avoid requiring tool_call_id
                        Msg toolMsg = new Msg("system", toolResults);
                        toolMsg.setAgentName(this.name);
                        currentHistory.add(toolMsg);
                        allReplies.add(toolMsg);
                    }
                    break;
                }
            }

            if (!foundToolCall) {
                break; // No tool calls, we're done
            }
        }

        return allReplies;
    }

    /**
     * Parse and execute tool calls from the model response.
     * Supports two formats:
     * 1. [TOOL_CALLS] toolName(args) toolName2(args2)
     * 2. JSON: {"tool": "name", "args": "..."}  or  tool_name: args
     */
    private String executeToolCalls(String content) {
        if (content == null || toolRegistry == null) return null;

        StringBuilder results = new StringBuilder();
        boolean found = false;

        // Format 1: [TOOL_CALLS] marker
        int idx = content.indexOf("[TOOL_CALLS]");
        if (idx >= 0) {
            String toolSection = content.substring(idx + "[TOOL_CALLS]".length()).trim();
            java.util.regex.Pattern p = java.util.regex.Pattern.compile("(\\w+)\\(([^)]*)\\)");
            java.util.regex.Matcher m = p.matcher(toolSection);
            while (m.find()) {
                String toolName = m.group(1);
                String args = m.group(2);
                if (toolRegistry.hasHandler(toolName)) {
                    String result = toolRegistry.executeTool(toolName, args);
                    if (result != null) {
                        results.append("[").append(toolName).append(" result]: ").append(result).append("\n");
                        found = true;
                    }
                }
            }
        }

        return found ? results.toString() : null;
    }

    private String buildFullPrompt() {
        String fullPrompt = sysPrompt;
        if (toolRegistry != null) {
            String toolsPrompt = toolRegistry.formatToolsPrompt();
            if (toolsPrompt != null && !toolsPrompt.isEmpty()) {
                if (fullPrompt != null && !fullPrompt.isEmpty()) {
                    fullPrompt = fullPrompt + "\n\n" + toolsPrompt;
                } else {
                    fullPrompt = toolsPrompt;
                }
            }
        }
        return fullPrompt;
    }
}
