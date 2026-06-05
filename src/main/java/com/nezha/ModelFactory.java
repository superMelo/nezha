package com.nezha;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ModelFactory {

    private final NezhaProperties properties;
    private final Map<String, LLMModel> cache = new ConcurrentHashMap<String, LLMModel>();

    public ModelFactory(NezhaProperties properties) {
        this.properties = properties;
    }

    public LLMModel getModel(String modelName) {
        LLMModel cached = cache.get(modelName);
        if (cached != null) {
            return cached;
        }

        NezhaProperties.ModelConfig config = properties.findModel(modelName);
        if (config == null) {
            throw new RuntimeException("Model not found: " + modelName);
        }

        LLMModel model = createModel(config);
        cache.put(modelName, model);
        return model;
    }

    public LLMModel getModelByProvider(String provider) {
        NezhaProperties.ModelConfig config = properties.findModelByProvider(provider);
        if (config == null) {
            throw new RuntimeException("No model found for provider: " + provider);
        }

        LLMModel cached = cache.get(config.getName());
        if (cached != null) {
            return cached;
        }

        LLMModel model = createModel(config);
        cache.put(config.getName(), model);
        return model;
    }

    public LLMModel getDefaultModel() {
        NezhaProperties.ModelConfig config = properties.getDefaultModel();
        if (config == null) {
            throw new RuntimeException("No default model configured");
        }

        LLMModel cached = cache.get(config.getName());
        if (cached != null) {
            return cached;
        }

        LLMModel model = createModel(config);
        cache.put(config.getName(), model);
        return model;
    }

    public void clearCache() {
        cache.clear();
    }

    public void clearCache(String modelName) {
        cache.remove(modelName);
    }

    private LLMModel createModel(NezhaProperties.ModelConfig config) {
        String provider = config.getProvider().toLowerCase();
        switch (provider) {
            case "claude":
                return new ClaudeModel(
                        config.getApiKey(),
                        config.getBaseUrl(),
                        config.getModel(),
                        config.getMaxTokens(),
                        config.getTemperature()
                );
            case "openai":
            case "deepseek":
            case "qwen":
            default:
                return new OpenAIModel(
                        config.getApiKey(),
                        config.getBaseUrl(),
                        config.getModel(),
                        config.getMaxTokens(),
                        config.getTemperature()
                );
        }
    }
}
