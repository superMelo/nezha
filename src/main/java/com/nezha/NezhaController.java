package com.nezha;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class NezhaController {

    private final NezhaProperties properties;
    private final AgentService agentService;
    private final ChatService chatService;
    private final MemoryService memoryService;
    private final TaskService taskService;
    private final CompressService compressService;
    private final PipelineService pipelineService;
    private final ModelFactory modelFactory;
    private final ToolRegistry toolRegistry;
    private final JdbcTemplate jdbc;

    public NezhaController(NezhaProperties properties,
                           AgentService agentService,
                           ChatService chatService,
                           MemoryService memoryService,
                           TaskService taskService,
                           CompressService compressService,
                           PipelineService pipelineService,
                           ModelFactory modelFactory,
                           ToolRegistry toolRegistry,
                           JdbcTemplate jdbc) {
        this.properties = properties;
        this.agentService = agentService;
        this.chatService = chatService;
        this.memoryService = memoryService;
        this.taskService = taskService;
        this.compressService = compressService;
        this.pipelineService = pipelineService;
        this.modelFactory = modelFactory;
        this.toolRegistry = toolRegistry;
        this.jdbc = jdbc;
    }

    // ==================== Agent Endpoints ====================

    @GetMapping("/api/agents")
    public Map<String, Object> listAgents() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("agents", agentService.listAgents());
        return result;
    }

    @PostMapping("/api/agents/custom")
    public Map<String, Object> createCustomAgent(@RequestBody Map<String, Object> body) {
        String name = (String) body.get("name");
        String sysPrompt = (String) body.get("systemPrompt");
        String modelName = (String) body.get("modelName");
        Object memorySizeObj = body.get("memorySize");
        int memorySize = 50;
        if (memorySizeObj != null) {
            memorySize = ((Number) memorySizeObj).intValue();
        }

        agentService.createCustomAgent(name, sysPrompt, modelName, memorySize);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("name", name);
        result.put("memorySize", memorySize);
        result.put("isCustom", true);
        return result;
    }

    @DeleteMapping("/api/agents/{name}")
    public Map<String, Object> deleteAgent(@PathVariable String name) {
        boolean deleted = agentService.deleteAgent(name);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", deleted);
        if (!deleted) {
            result.put("error", "Cannot delete built-in agent");
        }
        return result;
    }

    // ==================== Model Endpoints ====================

    @GetMapping("/api/models")
    public Map<String, Object> listModels() {
        List<Map<String, Object>> models = new ArrayList<Map<String, Object>>();
        for (NezhaProperties.ModelConfig config : properties.getModels()) {
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("name", config.getName());
            m.put("provider", config.getProvider());
            models.add(m);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("models", models);
        return result;
    }

    // ==================== Settings Endpoints ====================

    @GetMapping("/api/settings")
    public Map<String, Object> getSettings() {
        Map<String, Object> result = new HashMap<String, Object>();

        List<Map<String, Object>> rows = jdbc.queryForList("SELECT setting_key, setting_value FROM app_setting");
        Map<String, String> settings = new HashMap<String, String>();
        for (Map<String, Object> row : rows) {
            String key = (String) row.get("SETTING_KEY");
            String value = (String) row.get("SETTING_VALUE");
            settings.put(key, value);
        }

        Map<String, String> masked = new HashMap<String, String>();
        for (NezhaProperties.ModelConfig config : properties.getModels()) {
            String settingKey = "apiKey." + config.getName();
            if (settings.containsKey(settingKey) && settings.get(settingKey) != null) {
                masked.put(config.getName(), maskApiKey(settings.get(settingKey)));
            } else if (config.getApiKey() != null && !config.getApiKey().isEmpty()) {
                masked.put(config.getName(), maskApiKey(config.getApiKey()));
            } else {
                masked.put(config.getName(), "");
            }
        }
        result.put("apiKeys", masked);
        return result;
    }

    @PostMapping("/api/settings")
    public Map<String, Object> saveSettings(@RequestBody Map<String, Object> body) {
        @SuppressWarnings("unchecked")
        Map<String, String> apiKeys = (Map<String, String>) body.get("apiKeys");
        if (apiKeys != null) {
            for (Map.Entry<String, String> entry : apiKeys.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (value == null || value.isEmpty()) {
                    continue;
                }
                jdbc.update(
                        "MERGE INTO app_setting (setting_key, setting_value) KEY(setting_key) VALUES(?, ?)",
                        "apiKey." + key, value);

                NezhaProperties.ModelConfig config = properties.findModel(key);
                if (config != null) {
                    config.setApiKey(value);
                    modelFactory.clearCache(key);
                }
            }
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        return result;
    }

    // ==================== Session Endpoints ====================

    @GetMapping("/api/sessions")
    public Map<String, Object> listSessions() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("sessions", chatService.listSessions());
        return result;
    }

    @PostMapping("/api/sessions")
    public Map<String, Object> createSession(@RequestBody Map<String, Object> body) {
        String title = (String) body.get("title");
        String agentName = (String) body.getOrDefault("agentName", "Assistant");
        String pipelineName = (String) body.get("pipelineName");
        String modelName = (String) body.get("modelName");
        return chatService.createSession(title, agentName, modelName, pipelineName);
    }

    @DeleteMapping("/api/sessions/{id}")
    public Map<String, Object> deleteSession(@PathVariable Long id) {
        chatService.deleteSession(id);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        return result;
    }

    @GetMapping("/api/sessions/{id}/messages")
    public Map<String, Object> getMessages(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("messages", chatService.getMessages(id));
        return result;
    }

    // ==================== Chat Endpoint ====================

    @PostMapping("/api/chat")
    public Map<String, Object> chat(@RequestBody Map<String, Object> body) {
        String agentName = (String) body.getOrDefault("agentName", "Assistant");
        String pipelineName = (String) body.get("pipelineName");
        Object sessionIdObj = body.get("sessionId");
        String message = (String) body.get("message");

        if (message == null || message.trim().isEmpty()) {
            Map<String, Object> error = new HashMap<String, Object>();
            error.put("error", "Message cannot be empty");
            return error;
        }

        Long sessionId = null;
        if (sessionIdObj != null) {
            sessionId = ((Number) sessionIdObj).longValue();
        }

        if (sessionId == null) {
            String effectiveName = (pipelineName != null && !pipelineName.isEmpty()) ? pipelineName : agentName;
            Map<String, Object> session = chatService.createSession(null, effectiveName, null, pipelineName);
            sessionId = (Long) session.get("id");
        }

        Msg userMsg = new Msg("user", message);
        userMsg.setAgentName(agentName);
        chatService.saveMessage(sessionId, userMsg);

        List<Msg> replies;
        if (pipelineName != null && !pipelineName.isEmpty()) {
            replies = pipelineService.executePipeline(pipelineName, sessionId, message);
        } else {
            BaseAgent agent = agentService.getAgent(agentName);
            String sysPrompt = agentService.getAgentSysPrompt(agentName);
            String modelName = agentService.getAgentModelName(agentName);
            List<Msg> history = chatService.getMessages(sessionId);

            if (agent != null) {
                replies = agent.chat(history);
            } else {
                LLMModel model;
                if (modelName != null && !modelName.isEmpty()) {
                    model = modelFactory.getModel(modelName);
                } else {
                    model = modelFactory.getDefaultModel();
                }
                long startTime = System.currentTimeMillis();
                try {
                    replies = model.chat(sysPrompt, history);
                } catch (Exception e) {
                    replies = new ArrayList<Msg>();
                    Msg errorMsg = new Msg("assistant", "Error: " + e.getMessage());
                    errorMsg.setAgentName(agentName);
                    replies.add(errorMsg);
                }
                long elapsed = System.currentTimeMillis() - startTime;
                for (Msg reply : replies) {
                    reply.setAgentName(agentName);
                    reply.setElapsedMs(elapsed);
                }
            }
        }

        for (Msg reply : replies) {
            chatService.saveMessage(sessionId, reply);
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("sessionId", sessionId);
        result.put("replies", replies);
        return result;
    }

    // ==================== Memory Endpoints ====================

    @GetMapping("/api/memory/{agent}")
    public Map<String, Object> getMemory(@PathVariable String agent) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("memories", memoryService.getMemories(agent));
        return result;
    }

    @PostMapping("/api/memory")
    public Map<String, Object> addMemory(@RequestBody Map<String, Object> body) {
        String agentName = (String) body.get("agentName");
        String content = (String) body.get("content");
        String category = (String) body.get("category");
        Object importanceObj = body.get("importance");
        Integer importance = null;
        if (importanceObj != null) {
            importance = ((Number) importanceObj).intValue();
        }

        Long id = memoryService.addMemory(agentName, content, category, importance);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("id", id);
        result.put("success", true);
        return result;
    }

    @DeleteMapping("/api/memory/{id}")
    public Map<String, Object> deleteMemory(@PathVariable Long id) {
        memoryService.deleteMemory(id);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        return result;
    }

    // ==================== Task Endpoints ====================

    @GetMapping("/api/tasks")
    public Map<String, Object> listTasks() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("tasks", taskService.listTasks());
        return result;
    }

    @PostMapping("/api/tasks")
    public Map<String, Object> createTask(@RequestBody Map<String, Object> body) {
        String agentName = (String) body.get("agentName");
        String taskName = (String) body.get("taskName");
        String cronExpression = (String) body.get("cronExpression");
        String taskPrompt = (String) body.get("taskPrompt");
        Object enabledObj = body.get("enabled");
        Boolean enabled = null;
        if (enabledObj != null) {
            enabled = (Boolean) enabledObj;
        }

        Long id = taskService.createTask(agentName, taskName, cronExpression, taskPrompt, enabled);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("id", id);
        result.put("success", true);
        return result;
    }

    @DeleteMapping("/api/tasks/{id}")
    public Map<String, Object> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        return result;
    }

    @PostMapping("/api/tasks/{id}/toggle")
    public Map<String, Object> toggleTask(@PathVariable Long id) {
        taskService.toggleTask(id);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        return result;
    }

    // ==================== Pipeline Endpoints ====================

    @GetMapping("/api/pipelines")
    public Map<String, Object> listPipelines() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("pipelines", pipelineService.listPipelines());
        return result;
    }

    @PostMapping("/api/pipelines")
    public Map<String, Object> createPipeline(@RequestBody Map<String, Object> body) {
        String name = (String) body.get("name");
        String type = (String) body.get("type");
        String description = (String) body.get("description");
        String configJson = (String) body.get("configJson");
        Long id = pipelineService.createPipeline(name, type, description, configJson);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("id", id);
        result.put("success", true);
        return result;
    }

    @DeleteMapping("/api/pipelines/{id}")
    public Map<String, Object> deletePipeline(@PathVariable Long id) {
        pipelineService.deletePipeline(id);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        return result;
    }

    // ==================== Compress Endpoint ====================

    @PostMapping("/api/compress/{sessionId}")
    public Map<String, Object> compressSession(@PathVariable Long sessionId) {
        int removed = compressService.compressSession(sessionId);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("removed", removed);
        return result;
    }

    // ==================== Utility Methods ====================

    private String maskApiKey(String apiKey) {
        if (apiKey == null || apiKey.length() <= 8) {
            return "****";
        }
        return apiKey.substring(0, 4) + "****" + apiKey.substring(apiKey.length() - 4);
    }
}
