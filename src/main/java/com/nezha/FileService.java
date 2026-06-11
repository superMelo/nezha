package com.nezha;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

@Service
public class FileService {

    private final JdbcTemplate jdbc;
    private final String uploadDir;

    public FileService(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        // Upload directory relative to working directory
        this.uploadDir = System.getProperty("user.dir") + File.separator + "uploads";
        File dir = new File(this.uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public Map<String, Object> uploadFile(Long sessionId, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        String originalName = file.getOriginalFilename();
        String contentType = file.getContentType();
        long fileSize = file.getSize();

        // Generate unique stored name
        String ext = "";
        if (originalName != null && originalName.contains(".")) {
            ext = originalName.substring(originalName.lastIndexOf("."));
        }
        String storedName = System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 8) + ext;

        // Save file to disk
        try {
            File dest = new File(uploadDir + File.separator + storedName);
            file.transferTo(dest);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file: " + e.getMessage());
        }

        // Extract text content for text files
        final String textContent;
        String rawText = null;
        if (contentType != null && (contentType.startsWith("text/")
                || "application/json".equals(contentType)
                || contentType.contains("xml")
                || contentType.contains("javascript"))) {
            try {
                rawText = new String(file.getBytes(), "UTF-8");
                if (rawText.length() > 50000) rawText = rawText.substring(0, 50000) + "\n...[truncated]";
            } catch (Exception e) {
                rawText = "[Failed to read text content]";
            }
        }
        if (rawText == null && originalName != null) {
            String lower = originalName.toLowerCase();
            if (lower.endsWith(".csv") || lower.endsWith(".txt") || lower.endsWith(".md")
                    || lower.endsWith(".json") || lower.endsWith(".xml") || lower.endsWith(".yaml")
                    || lower.endsWith(".yml") || lower.endsWith(".log") || lower.endsWith(".py")
                    || lower.endsWith(".java") || lower.endsWith(".js") || lower.endsWith(".sql")) {
                try {
                    rawText = new String(file.getBytes(), "UTF-8");
                    if (rawText.length() > 50000) rawText = rawText.substring(0, 50000) + "\n...[truncated]";
                } catch (Exception e) {
                    rawText = "[Failed to read text content]";
                }
            }
        }
        textContent = rawText;

        // Save metadata to DB
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(new org.springframework.jdbc.core.PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(java.sql.Connection con) throws java.sql.SQLException {
                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO session_file (session_id, original_name, stored_name, file_size, content_type, text_content) "
                                + "VALUES (?, ?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, sessionId);
                ps.setString(2, originalName);
                ps.setString(3, storedName);
                ps.setLong(4, fileSize);
                ps.setString(5, contentType);
                ps.setString(6, textContent);
                return ps;
            }
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("id", id);
        result.put("sessionId", sessionId);
        result.put("originalName", originalName);
        result.put("storedName", storedName);
        result.put("fileSize", fileSize);
        result.put("contentType", contentType);
        result.put("hasTextContent", textContent != null);
        result.put("success", true);
        return result;
    }

    public List<Map<String, Object>> listFiles(Long sessionId) {
        List<Map<String, Object>> rows = jdbc.queryForList(
                "SELECT id, session_id, original_name, stored_name, file_size, content_type, text_content, created_at "
                        + "FROM session_file WHERE session_id = ? ORDER BY created_at DESC", sessionId);
        List<Map<String, Object>> files = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> row : rows) {
            Map<String, Object> file = new HashMap<String, Object>();
            file.put("id", row.get("ID"));
            file.put("sessionId", row.get("SESSION_ID"));
            file.put("originalName", row.get("ORIGINAL_NAME"));
            file.put("storedName", row.get("STORED_NAME"));
            file.put("fileSize", row.get("FILE_SIZE"));
            file.put("contentType", row.get("CONTENT_TYPE"));
            file.put("textContent", row.get("TEXT_CONTENT"));
            file.put("hasTextContent", row.get("TEXT_CONTENT") != null);
            file.put("createdAt", row.get("CREATED_AT"));
            files.add(file);
        }
        return files;
    }

    public Map<String, Object> getFile(Long fileId) {
        List<Map<String, Object>> rows = jdbc.queryForList(
                "SELECT id, session_id, original_name, stored_name, file_size, content_type, text_content "
                        + "FROM session_file WHERE id = ?", fileId);
        if (rows.isEmpty()) return null;
        Map<String, Object> row = rows.get(0);
        Map<String, Object> file = new HashMap<String, Object>();
        file.put("id", row.get("ID"));
        file.put("sessionId", row.get("SESSION_ID"));
        file.put("originalName", row.get("ORIGINAL_NAME"));
        file.put("storedName", row.get("STORED_NAME"));
        file.put("fileSize", row.get("FILE_SIZE"));
        file.put("contentType", row.get("CONTENT_TYPE"));
        file.put("textContent", row.get("TEXT_CONTENT"));
        return file;
    }

    public String getFilePath(Long fileId) {
        List<Map<String, Object>> rows = jdbc.queryForList(
                "SELECT stored_name FROM session_file WHERE id = ?", fileId);
        if (rows.isEmpty()) return null;
        String storedName = (String) rows.get(0).get("STORED_NAME");
        return uploadDir + File.separator + storedName;
    }

    public void deleteFile(Long fileId) {
        Map<String, Object> file = getFile(fileId);
        if (file != null) {
            String storedName = (String) file.get("storedName");
            File f = new File(uploadDir + File.separator + storedName);
            if (f.exists()) {
                f.delete();
            }
        }
        jdbc.update("DELETE FROM session_file WHERE id = ?", fileId);
    }

    public String formatFileSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return String.format("%.1f KB", bytes / 1024.0);
        return String.format("%.1f MB", bytes / (1024.0 * 1024.0));
    }
}
