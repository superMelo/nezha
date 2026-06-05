package com.nezha;

import java.util.ArrayList;
import java.util.List;

/**
 * Sequential Pipeline: passes the message through a series of agents in order.
 * Each agent receives the output of the previous agent as its input.
 */
public class SequentialPipeline extends Pipeline {

    private List<String> agentNames;

    public SequentialPipeline(String name, String description) {
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
        String currentMsg = message;

        for (String agentName : agentNames) {
            context.setCurrentAgentName(agentName);
            context.setCurrentMessage(currentMsg);

            // Get the agent and run it
            BaseAgent agent = getAgentFromContext(agentName, context);
            if (agent == null) {
                Msg errMsg = new Msg("system", "Agent not found: " + agentName);
                errMsg.setElapsedMs(0L);
                allMessages.add(errMsg);
                continue;
            }

            // Build minimal history with the current message
            List<Msg> history = new ArrayList<Msg>();
            history.add(new Msg("user", currentMsg));

            List<Msg> replies = agent.chat(history);
            allMessages.addAll(replies);

            // Feed the last reply as input for the next agent
            if (!replies.isEmpty()) {
                currentMsg = replies.get(replies.size() - 1).getContent();
            }
        }

        return allMessages;
    }

    private BaseAgent getAgentFromContext(String name, PipelineContext ctx) {
        // This will be resolved by PipelineService at runtime
        Object agentObj = ctx.getVariable("agent." + name);
        if (agentObj instanceof BaseAgent) {
            return (BaseAgent) agentObj;
        }
        return null;
    }
}
