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

    public BaseAgent(ModelFactory modelFactory, ToolRegistry toolRegistry) {
        this.modelFactory = modelFactory;
        this.toolRegistry = toolRegistry;
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
        LLMModel model;
        if (modelName != null && !modelName.isEmpty()) {
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
