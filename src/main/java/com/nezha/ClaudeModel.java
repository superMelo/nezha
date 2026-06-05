package com.nezha;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ClaudeModel implements LLMModel {

    private static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
    private static final String API_VERSION = "2023-06-01";

    private final String apiKey;
    private final String model;
    private final int maxTokens;
    private final double temperature;
    private final OkHttpClient client;
    private final ObjectMapper mapper;

    public ClaudeModel(String apiKey, String baseUrl, String model, int maxTokens, double temperature) {
        this.apiKey = apiKey;
        this.model = model;
        this.maxTokens = maxTokens;
        this.temperature = temperature;
        this.client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
        this.mapper = new ObjectMapper();
    }

    @Override
    public List<Msg> chat(String sysPrompt, List<Msg> history) {
        try {
            ObjectNode body = mapper.createObjectNode();
            body.put("model", this.model);
            body.put("max_tokens", this.maxTokens);
            body.put("temperature", this.temperature);

            ArrayNode messages = body.putArray("messages");

            if (sysPrompt != null && !sysPrompt.isEmpty()) {
                ObjectNode sysMsg = messages.addObject();
                sysMsg.put("role", "user");
                sysMsg.put("content", sysPrompt);
            }

            for (Msg msg : history) {
                if (sysPrompt != null && !sysPrompt.isEmpty() && msg.getRole().equals("system")) {
                    continue;
                }
                ObjectNode m = messages.addObject();
                m.put("role", msg.getRole());
                m.put("content", msg.getContent());
            }

            // If system prompt was added as first user message and we have history,
            // Claude needs a separate system field
            // Reset and do it properly
            body.remove("messages");
            ArrayNode msgArray = body.putArray("messages");

            if (sysPrompt != null && !sysPrompt.isEmpty()) {
                body.put("system", sysPrompt);
            }

            for (Msg msg : history) {
                ObjectNode m = msgArray.addObject();
                m.put("role", msg.getRole());
                m.put("content", msg.getContent());
            }

            String json = mapper.writeValueAsString(body);

            String anthropicBaseUrl = "https://api.anthropic.com";
            String url = anthropicBaseUrl + "/v1/messages";

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("x-api-key", this.apiKey)
                    .addHeader("anthropic-version", API_VERSION)
                    .addHeader("Content-Type", "application/json")
                    .post(RequestBody.create(json, JSON_TYPE))
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String respBody = response.body() != null ? response.body().string() : "";
                if (!response.isSuccessful()) {
                    throw new RuntimeException("Claude API error " + response.code() + ": " + respBody);
                }

                JsonNode root = mapper.readTree(respBody);
                JsonNode content = root.get("content");
                StringBuilder sb = new StringBuilder();
                if (content != null && content.isArray()) {
                    for (JsonNode block : content) {
                        if ("text".equals(block.get("type").asText())) {
                            sb.append(block.get("text").asText());
                        }
                    }
                }

                List<Msg> result = new ArrayList<Msg>();
                Msg reply = new Msg("assistant", sb.toString());
                result.add(reply);
                return result;
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to call Claude API: " + e.getMessage(), e);
        }
    }
}
