package com.nezha;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Model Router: selects the best model for each query based on complexity.
 * Simple queries → cheaper model, complex queries → better model.
 */
@Service
public class ModelRouter {

    private final ModelFactory modelFactory;

    // Complexity indicators
    private static final List<String> COMPLEX_KEYWORDS = Arrays.asList(
            "explain", "analyze", "compare", "design", "implement", "review",
            "debug", "optimize", "architecture", "refactor", "algorithm",
            "rewrite", "summarize", "translate", "calculate", "evaluate"
    );

    private static final int SHORT_THRESHOLD = 10;  // words
    private static final int MEDIUM_THRESHOLD = 50;  // words

    // Model tiers
    private String cheapModel = null;   // null = use default (only one DeepSeek model configured)
    private String defaultModel = "deepseek";
    private String premiumModel = null;

    public ModelRouter(ModelFactory modelFactory) {
        this.modelFactory = modelFactory;
    }

    /**
     * Route a message to the appropriate model.
     * Returns the model name to use.
     */
    public String route(String message) {
        if (message == null || message.isEmpty()) return defaultModel;

        int wordCount = message.split("\\s+").length;
        boolean hasCode = message.contains("```") || message.contains("class ")
                || message.contains("def ") || message.contains("function ");
        boolean isComplex = isComplexQuery(message);

        // Complex or long queries → premium model (if available)
        if (isComplex && premiumModel != null) {
            return premiumModel;
        }

        // Short simple queries → cheap model (if available)
        if (wordCount < SHORT_THRESHOLD && !hasCode && !isComplex && cheapModel != null) {
            return cheapModel;
        }

        // Medium complexity → default model
        return defaultModel;
    }

    /**
     * Get the LLMModel instance for the routed model.
     */
    public LLMModel getModel(String message) {
        String modelName = route(message);
        LLMModel model = modelFactory.getModel(modelName);
        return model != null ? model : modelFactory.getDefaultModel();
    }

    private boolean isComplexQuery(String message) {
        String lower = message.toLowerCase();
        for (String keyword : COMPLEX_KEYWORDS) {
            if (lower.contains(keyword)) return true;
        }
        return message.split("\\s+").length > MEDIUM_THRESHOLD;
    }

    // Setters for configuration
    public void setCheapModel(String cheapModel) { this.cheapModel = cheapModel; }
    public void setDefaultModel(String defaultModel) { this.defaultModel = defaultModel; }
    public void setPremiumModel(String premiumModel) { this.premiumModel = premiumModel; }
}
