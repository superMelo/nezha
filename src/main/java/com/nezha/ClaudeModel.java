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
    private final String baseUrl;
    private final String model;
    private final int maxTokens;
    private final double temperature;
    private final OkHttpClient client;
    private final ObjectMapper mapper;

    public ClaudeModel(String apiKey, String baseUrl, String model, int maxTokens, double temperature) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl != null && !baseUrl.isEmpty() ? baseUrl : "https://api.anthropic.com";
        this.model = model;
        this.maxTokens = maxTokens;
        this.temperature = temperature;
        this.client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .proxy(java.net.Proxy.NO_PROXY)
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

            // Build messages array — filter out system-role messages from history
            // (Claude Messages API requires system in top-level "system" field, not in messages)
            ArrayNode msgArray = body.putArray("messages");
            for (Msg msg : history) {
                if ("system".equals(msg.getRole())) {
                    continue; // skip system messages — handled by top-level field
                }
                ObjectNode m = msgArray.addObject();
                m.put("role", msg.getRole());
                // Claude API requires content to be a non-empty string
                String content = msg.getContent();
                m.put("content", content != null ? content : "");
            }

            // Claude uses top-level "system" field, not a system message in the array
            if (sysPrompt != null && !sysPrompt.isEmpty()) {
                body.put("system", sysPrompt);
            }

            String json = mapper.writeValueAsString(body);

            String url = this.baseUrl + "/v1/messages";

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
                        String blockType = block.get("type").asText();
                        if ("text".equals(blockType)) {
                            sb.append(block.get("text").asText());
                        } else if ("tool_use".equals(blockType)) {
                            // Serialize tool_use block as JSON so callers can parse it
                            sb.append("[TOOL_USE:").append(mapper.writeValueAsString(block)).append("]");
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
