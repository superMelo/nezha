package com.nezha;

import java.util.ArrayList;
import java.util.List;

/**
 * Broadcast Pipeline: sends the same message to multiple agents in parallel (sequentially for now),
 * collecting all their replies.
 */
public class BroadcastPipeline extends Pipeline {

    private List<String> agentNames;

    public BroadcastPipeline(String name, String description) {
        super(name, description);
        this.agentNames = new ArrayList<String>();
    }

    public List<String> getAgentNames() { return agentNames; }

    public void addAgent(String agentName) {
        agentNames.add(agentName);
    }

    @Override
    public List<Msg> execute(String message, PipelineContext context) {
        List<Msg> allMessages = new ArrayList<Msg>();

        for (String agentName : agentNames) {
            context.setCurrentAgentName(agentName);
            context.setCurrentMessage(message);

            BaseAgent agent = getAgentFromContext(agentName, context);
            if (agent == null) {
                Msg errMsg = new Msg("system", "Agent not found: " + agentName);
                errMsg.setElapsedMs(0L);
                allMessages.add(errMsg);
                continue;
            }

            List<Msg> history = new ArrayList<Msg>();
            history.add(new Msg("user", message));

            List<Msg> replies = agent.chat(history);
            allMessages.addAll(replies);
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
}
