package com.nezha;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Base class for Pipeline orchestration.
 * A Pipeline takes an initial message and routes it through one or more agents.
 */
public abstract class Pipeline {

    protected String name;
    protected String description;

    public Pipeline(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    /**
     * Execute the pipeline.
     * @param message the initial user message
     * @param context execution context with agent references, etc.
     * @return list of messages produced by the pipeline
     */
    public abstract List<Msg> execute(String message, PipelineContext context);
}
