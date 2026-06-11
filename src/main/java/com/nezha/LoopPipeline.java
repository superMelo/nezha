package com.nezha;

import java.util.ArrayList;
import java.util.List;

/**
 * Loop Pipeline: repeatedly passes the message through an agent until a condition is met.
 */
public class LoopPipeline extends Pipeline {

    private String agentName;
    private int maxIterations;
    private String exitCondition;    // "done" (default), "pass", or a keyword to match

    public LoopPipeline(String name, String description) {
        super(name, description);
        this.maxIterations = 20;
        this.exitCondition = "done";
    }

    public String getAgentName() { return agentName; }
    public void setAgentName(String agentName) { this.agentName = agentName; }

    public int getMaxIterations() { return maxIterations; }
    public void setMaxIterations(int maxIterations) { this.maxIterations = maxIterations; }

    public String getExitCondition() { return exitCondition; }
    public void setExitCondition(String exitCondition) { this.exitCondition = exitCondition; }

    @Override
    public List<Msg> execute(String message, PipelineContext context) {
        List<Msg> allMessages = new ArrayList<Msg>();
        String currentMsg = message;

        for (int i = 0; i < maxIterations; i++) {
            context.setCurrentAgentName(agentName);
            context.setCurrentMessage(currentMsg);
            context.setVariable("iteration", i);

            BaseAgent agent = getAgentFromContext(agentName, context);
            if (agent == null) {
                Msg errMsg = new Msg("system", "Agent not found: " + agentName);
                errMsg.setElapsedMs(0L);
                allMessages.add(errMsg);
                break;
            }

            List<Msg> history = new ArrayList<Msg>();
            history.add(new Msg("user", currentMsg));

            List<Msg> replies = agent.chat(history);
            allMessages.addAll(replies);

            if (!replies.isEmpty()) {
                currentMsg = replies.get(replies.size() - 1).getContent();
            }

            // Check if the agent signals completion
            // Convention: if reply contains exit marker, the loop ends
            if (!replies.isEmpty()) {
                String lastContent = replies.get(replies.size() - 1).getContent();
                if (lastContent != null) {
                    if (meetsExitCondition(lastContent)) {
                        break;
                    }
                }
            }
        }

        return allMessages;
    }

    private BaseAgent getAgentFromContext(String name, PipelineContext ctx) {
        Object agentObj = ctx.getVariable("agent." + name);
        if (agentObj instanceof BaseAgent) {
            return (BaseAgent) agentObj;
        }
        return null;
    }

    private boolean meetsExitCondition(String content) {
        if (exitCondition == null || "done".equals(exitCondition)) {
            return content.startsWith("[DONE]") || content.contains("[DONE]");
        }
        // Custom exit condition: match keyword (case-insensitive)
        String lowerContent = content.toLowerCase();
        String lowerCond = exitCondition.toLowerCase();
        return lowerContent.contains(lowerCond);
    }
}
