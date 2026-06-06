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
        return chat(history, routedModel);
    }

    public List<Msg> chat(List<Msg> history, String overrideModel) {
        LLMModel model;
        if (overrideModel != null && !overrideModel.isEmpty()) {
            model = modelFactory.getModel(overrideModel);
        } else if (modelName != null && !modelName.isEmpty()) {
            model = modelFactory.getModel(modelName);
        } else {
            model = modelFactory.getDefaultModel();
        }

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

        long startTime = System.currentTimeMillis();
        try {
            List<Msg> result = model.chat(fullPrompt, history);
            long elapsed = System.currentTimeMillis() - startTime;

            for (Msg msg : result) {
                msg.setAgentName(this.name);
                msg.setElapsedMs(elapsed);
            }
            return result;
        } catch (Exception e) {
            long elapsed = System.currentTimeMillis() - startTime;
            List<Msg> result = new ArrayList<Msg>();
            Msg errorMsg = new Msg("assistant", "Error: " + e.getMessage());
            errorMsg.setAgentName(this.name);
            errorMsg.setElapsedMs(elapsed);
            result.add(errorMsg);
            return result;
        }
    }
}
