package com.nezha;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties("nezha")
public class NezhaProperties {

    private List<ModelConfig> models = new ArrayList<>();

    public List<ModelConfig> getModels() {
        return models;
    }

    public void setModels(List<ModelConfig> models) {
        this.models = models;
    }

    public ModelConfig findModel(String name) {
        for (ModelConfig m : models) {
            if (m.getName().equals(name)) {
                return m;
            }
        }
        return null;
    }

    public ModelConfig findModelByProvider(String provider) {
        for (ModelConfig m : models) {
            if (m.getProvider().equals(provider)) {
                return m;
            }
        }
        return null;
    }

    public ModelConfig getDefaultModel() {
        if (!models.isEmpty()) {
            return models.get(0);
        }
        return null;
    }

    public static class ModelConfig {
        private String name;
        private String provider;
        private String apiKey;
        private String baseUrl;
        private String model;
        private int maxTokens = 4096;
        private double temperature = 0.7;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProvider() {
            return provider;
        }

        public void setProvider(String provider) {
            this.provider = provider;
        }

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public int getMaxTokens() {
            return maxTokens;
        }

        public void setMaxTokens(int maxTokens) {
            this.maxTokens = maxTokens;
        }

        public double getTemperature() {
            return temperature;
        }

        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }
    }
}
