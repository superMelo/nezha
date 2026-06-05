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

public class OpenAIModel implements LLMModel {

    private static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");

    private final String apiKey;
    private final String baseUrl;
    private final String model;
    private final int maxTokens;
    private final double temperature;
    private final OkHttpClient client;
    private final ObjectMapper mapper;

    public OpenAIModel(String apiKey, String baseUrl, String model, int maxTokens, double temperature) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
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
                sysMsg.put("role", "system");
                sysMsg.put("content", sysPrompt);
            }

            for (Msg msg : history) {
                ObjectNode m = messages.addObject();
                m.put("role", msg.getRole());
                m.put("content", msg.getContent());
            }

            String json = mapper.writeValueAsString(body);

            Request request = new Request.Builder()
                    .url(this.baseUrl + "/chat/completions")
                    .addHeader("Authorization", "Bearer " + this.apiKey)
                    .addHeader("Content-Type", "application/json")
                    .post(RequestBody.create(json, JSON_TYPE))
                    .build();

            try (Response response = client.newCall(request).execute()) {
                String respBody = response.body() != null ? response.body().string() : "";
                if (!response.isSuccessful()) {
                    throw new RuntimeException("OpenAI API error " + response.code() + ": " + respBody);
                }

                JsonNode root = mapper.readTree(respBody);
                JsonNode choices = root.get("choices");
                if (choices != null && choices.size() > 0) {
                    JsonNode message = choices.get(0).get("message");
                    String content = message.get("content").asText();
                    List<Msg> result = new ArrayList<Msg>();
                    Msg reply = new Msg("assistant", content);
                    result.add(reply);
                    return result;
                }

                throw new RuntimeException("No choices in OpenAI response");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to call OpenAI compatible API: " + e.getMessage(), e);
        }
    }
}
