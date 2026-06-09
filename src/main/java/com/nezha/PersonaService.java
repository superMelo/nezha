package com.nezha;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Persona Service - preset role templates for quick agent configuration.
 * Mirrors OpenClaw's SOUL.md / Persona system.
 */
@Service
public class PersonaService {

    private final JdbcTemplate jdbc;

    // Built-in persona templates
    private static final String[][] BUILTINS = {
            {"coder", "Code Expert",
                    "You are an expert programmer. Write clean, efficient, well-documented code. "
                            + "Always explain your approach before writing code. "
                            + "Follow best practices and design patterns. "
                            + "Consider edge cases and error handling."},
            {"translator", "Translator",
                    "You are a professional translator fluent in multiple languages. "
                            + "Provide accurate, natural-sounding translations. "
                            + "When translating, preserve the tone, style, and cultural nuances of the original. "
                            + "If the input is ambiguous, ask for clarification."},
            {"analyst", "Data Analyst",
                    "You are a data analyst expert. Help users analyze data, create reports, "
                            + "and extract insights. Provide clear visualizations suggestions. "
                            + "Always back up your analysis with evidence and data points."},
            {"tutor", "Study Tutor",
                    "You are a patient and encouraging tutor. Explain concepts step by step. "
                            + "Use examples and analogies to make complex topics easy to understand. "
                            + "Ask questions to check understanding. Adapt your teaching style to the learner."},
            {"writer", "Creative Writer",
                    "You are a skilled creative writer. Help with stories, articles, essays, "
                            + "marketing copy, and other writing tasks. "
                            + "Pay attention to voice, tone, and audience. "
                            + "Offer revisions and alternatives when appropriate."},
            {"reviewer", "Code Reviewer",
                    "You are a senior code reviewer. Analyze code for bugs, performance issues, "
                            + "security vulnerabilities, and style improvements. "
                            + "Provide constructive feedback with specific suggestions and code examples."}
    };

    public PersonaService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        initDefaults();
    }

    private void initDefaults() {
        for (String[] p : BUILTINS) {
            jdbc.update("MERGE INTO persona_template (name, display_name, system_prompt, is_builtin) "
                            + "KEY(name) VALUES(?, ?, ?, TRUE)",
                    p[0], p[1], p[2]);
        }
    }

    public List<Map<String, Object>> listPersonas() {
        List<Map<String, Object>> rows = jdbc.queryForList(
                "SELECT name, display_name, system_prompt, is_builtin, created_at "
                        + "FROM persona_template ORDER BY is_builtin DESC, name ASC");

        List<Map<String, Object>> personas = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> row : rows) {
            Map<String, Object> p = new HashMap<String, Object>();
            p.put("name", row.get("NAME"));
            p.put("displayName", row.get("DISPLAY_NAME"));
            p.put("systemPrompt", row.get("SYSTEM_PROMPT"));
            p.put("isBuiltin", row.get("IS_BUILTIN"));
            p.put("createdAt", row.get("CREATED_AT"));
            personas.add(p);
        }
        return personas;
    }

    public Map<String, Object> getPersona(String name) {
        List<Map<String, Object>> rows = jdbc.queryForList(
                "SELECT name, display_name, system_prompt, is_builtin "
                        + "FROM persona_template WHERE name = ?", name);
        if (rows.isEmpty()) return null;

        Map<String, Object> row = rows.get(0);
        Map<String, Object> p = new HashMap<String, Object>();
        p.put("name", row.get("NAME"));
        p.put("displayName", row.get("DISPLAY_NAME"));
        p.put("systemPrompt", row.get("SYSTEM_PROMPT"));
        p.put("isBuiltin", row.get("IS_BUILTIN"));
        return p;
    }

    public Long createPersona(String name, String displayName, String systemPrompt) {
        jdbc.update("INSERT INTO persona_template (name, display_name, system_prompt, is_builtin) "
                        + "VALUES (?, ?, ?, FALSE)", name, displayName, systemPrompt);

        // Get the auto-generated id
        List<Map<String, Object>> rows = jdbc.queryForList(
                "SELECT id FROM persona_template WHERE name = ?", name);
        if (!rows.isEmpty()) {
            return ((Number) rows.get(0).get("ID")).longValue();
        }
        return null;
    }

    public boolean deletePersona(String name) {
        int deleted = jdbc.update("DELETE FROM persona_template WHERE name = ? AND is_builtin = FALSE", name);
        return deleted > 0;
    }

    /**
     * Apply a persona template to an agent's system prompt.
     */
    public boolean applyPersona(String agentName, String personaName) {
        Map<String, Object> persona = getPersona(personaName);
        if (persona == null) return false;

        String sysPrompt = (String) persona.get("systemPrompt");
        jdbc.update("UPDATE agent_config SET system_prompt = ? WHERE name = ?", sysPrompt, agentName);
        return true;
    }
}
