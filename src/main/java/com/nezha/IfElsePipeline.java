package com.nezha;

import java.util.ArrayList;
import java.util.List;

/**
 * IfElse Pipeline: evaluates a condition and routes to one of two sub-pipelines.
 */
public class IfElsePipeline extends Pipeline {

    private String conditionVariable;
    private Pipeline thenPipeline;
    private Pipeline elsePipeline;

    public IfElsePipeline(String name, String description) {
        super(name, description);
    }

    public String getConditionVariable() { return conditionVariable; }
    public void setConditionVariable(String var) { this.conditionVariable = var; }

    public Pipeline getThenPipeline() { return thenPipeline; }
    public void setThenPipeline(Pipeline pipeline) { this.thenPipeline = pipeline; }

    public Pipeline getElsePipeline() { return elsePipeline; }
    public void setElsePipeline(Pipeline pipeline) { this.elsePipeline = pipeline; }

    @Override
    public List<Msg> execute(String message, PipelineContext context) {
        // Evaluate condition from context variables
        Object condition = context.getVariable(conditionVariable);

        if (isTruthy(condition)) {
            if (thenPipeline != null) {
                return thenPipeline.execute(message, context);
            }
        } else {
            if (elsePipeline != null) {
                return elsePipeline.execute(message, context);
            }
        }

        // No matching branch
        List<Msg> empty = new ArrayList<Msg>();
        return empty;
    }

    private boolean isTruthy(Object value) {
        if (value == null) return false;
        if (value instanceof Boolean) return (Boolean) value;
        if (value instanceof String) return !((String) value).isEmpty();
        if (value instanceof Number) return ((Number) value).intValue() != 0;
        return true;
    }
}
