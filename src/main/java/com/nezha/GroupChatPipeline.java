package com.nezha;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * GroupChat Pipeline: sends the same message to multiple agents with FULL shared conversation history.
 * Unlike BroadcastPipeline (where each agent sees only the user message in isolation),
 * GroupChat lets every agent see all previous messages including other agents' replies —
 * like a WeChat group where everyone sees the full discussion.
 */
public class GroupChatPipeline extends Pipeline {

    private List<String> agentNames;

    public GroupChatPipeline(String name, String description) {
        super(name, description);
        this.agentNames = new ArrayList<String>();
    }

    public List<String> getAgentNames() { return agentNames; }

    public void addAgent(String agentName) {
        agentNames.add(agentName);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Msg> execute(String message, PipelineContext context) {
        List<Msg> allMessages = new ArrayList<Msg>();

        // Load full session history from context (set by PipelineService)
        List<Msg> fullHistory = (List<Msg>) context.getVariable("fullHistory");
        if (fullHistory == null) {
            fullHistory = new ArrayList<Msg>();
        }

        // Parse @mentions from the user message
        Set<String> mentionedAgents = new LinkedHashSet<String>();
        Matcher m = Pattern.compile("@(\\S+)").matcher(message);
        while (m.find()) {
            String mentioned = m.group(1);
            // Check if this matches one of our agent names
            for (String an : agentNames) {
                if (an.equals(mentioned)) {
                    mentionedAgents.add(an);
                    break;
                }
            }
        }

        // Determine which agents to call
        List<String> targetAgents;
        String mentionNote = "";
        if (!mentionedAgents.isEmpty()) {
            targetAgents = new ArrayList<String>(mentionedAgents);
            // Build mention context so agents know who was specifically addressed
            StringBuilder sb = new StringBuilder("[System note: ");
            sb.append("The user specifically @mentioned: ");
            for (String mn : mentionedAgents) {
                sb.append(mn).append(", ");
            }
            sb.setLength(sb.length() - 2); // remove trailing comma
            sb.append(". Only the mentioned agent(s) should respond. Other agents should NOT reply.]");
            mentionNote = sb.toString();
        } else {
            targetAgents = new ArrayList<String>(agentNames);
        }

        // Build shared history: full DB history + current user message (+ mention note if any)
        List<Msg> sharedHistory = new ArrayList<Msg>(fullHistory);
        if (!mentionNote.isEmpty()) {
            sharedHistory.add(new Msg("system", mentionNote));
        }
        sharedHistory.add(new Msg("user", message));

        // Call each target agent with the full shared context
        for (String agentName : targetAgents) {
            context.setCurrentAgentName(agentName);
            context.setCurrentMessage(message);

            BaseAgent agent = getAgentFromContext(agentName, context);
            if (agent == null) {
                Msg errMsg = new Msg("system", "Agent not found: " + agentName);
                errMsg.setElapsedMs(0L);
                allMessages.add(errMsg);
                continue;
            }

            // Give the agent the FULL shared history
            List<Msg> replies = agent.chat(sharedHistory);
            allMessages.addAll(replies);

            // Append this agent's replies to shared history
            // so subsequent agents can see and reference them
            sharedHistory.addAll(replies);
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
