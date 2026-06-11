package com.nezha;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * System Tool Service — provides Claude Code-like capabilities:
 * read_file, write_file, edit_file, run_command, search_files, search_content, web_search, web_fetch
 */
@Service
public class SystemToolService {

    private final JdbcTemplate jdbc;
    private final ToolRegistry toolRegistry;

    // Default workspace root — all file operations are sandboxed under this
    private static final String WORKSPACE_ROOT = System.getProperty("user.dir");

    public SystemToolService(JdbcTemplate jdbc, ToolRegistry toolRegistry) {
        this.jdbc = jdbc;
        this.toolRegistry = toolRegistry;
        registerTools();
    }

    private void registerTools() {
        // 1. Read file
        toolRegistry.registerTool(
            new ToolRegistry.ToolDefinition("read_file", "Read a file from the filesystem. Returns content with line numbers.", "read_file", "path, offset?, limit?"),
            args -> {
                String[] parts = parseArgs(args, 3);
                String path = resolvePath(parts[0]);
                int offset = parts[1] != null ? Integer.parseInt(parts[1]) : 0;
                int limit = parts[2] != null ? Integer.parseInt(parts[2]) : 2000;
                return readFile(path, offset, limit);
            }
        );

        // 2. Write file
        toolRegistry.registerTool(
            new ToolRegistry.ToolDefinition("write_file", "Create or overwrite a file with content. Use absolute path.", "write_file", "path, content"),
            args -> {
                String[] parts = parseArgs(args, 2);
                String path = resolvePath(parts[0]);
                String content = parts[1];
                if (content == null || content.isEmpty()) return "Error: content is required";
                return writeFile(path, content);
            }
        );

        // 3. Edit file (exact string replacement)
        toolRegistry.registerTool(
            new ToolRegistry.ToolDefinition("edit_file", "Replace exact string in a file. Use for precise edits.", "edit_file", "path, old_string, new_string"),
            args -> {
                String[] parts = parseArgs(args, 3);
                String path = resolvePath(parts[0]);
                String oldStr = parts[1];
                String newStr = parts[2];
                if (oldStr == null || newStr == null) return "Error: old_string and new_string are required";
                return editFile(path, oldStr, newStr);
            }
        );

        // 4. Run shell command
        toolRegistry.registerTool(
            new ToolRegistry.ToolDefinition("run_command", "Execute a shell command. Returns stdout and stderr. Timeout: 60s.", "run_command", "command"),
            args -> runCommand(args)
        );

        // 5. Search files (glob)
        toolRegistry.registerTool(
            new ToolRegistry.ToolDefinition("search_files", "Find files matching a glob pattern (e.g., **/*.java, src/**/*.ts).", "search_files", "pattern, path?"),
            args -> {
                String[] parts = parseArgs(args, 2);
                String pattern = parts[0];
                String basePath = parts[1] != null ? resolvePath(parts[1]) : WORKSPACE_ROOT;
                return globSearch(pattern, basePath);
            }
        );

        // 6. Search content (grep)
        toolRegistry.registerTool(
            new ToolRegistry.ToolDefinition("search_content", "Search file contents using regex pattern. Like grep/rg.", "search_content", "pattern, path?"),
            args -> {
                String[] parts = parseArgs(args, 2);
                String pattern = parts[0];
                String basePath = parts[1] != null ? resolvePath(parts[1]) : WORKSPACE_ROOT;
                return grepSearch(pattern, basePath);
            }
        );

        // 7. List directory
        toolRegistry.registerTool(
            new ToolRegistry.ToolDefinition("list_dir", "List files and directories at a given path.", "list_dir", "path?"),
            args -> {
                String path = args != null && !args.trim().isEmpty() ? resolvePath(args.trim()) : WORKSPACE_ROOT;
                return listDirectory(path);
            }
        );
    }

    // ==================== IMPLEMENTATIONS ====================

    private String readFile(String path, int offset, int limit) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            StringBuilder sb = new StringBuilder();
            int end = Math.min(offset + limit, lines.size());
            for (int i = offset; i < end; i++) {
                sb.append(String.format("%6d\t%s%n", i + 1, lines.get(i)));
            }
            if (end < lines.size()) {
                sb.append("... (").append(lines.size() - end).append(" more lines)");
            }
            return sb.toString();
        } catch (IOException e) {
            return "Error reading file: " + e.getMessage();
        }
    }

    private String writeFile(String path, String content) {
        try {
            // Unescape common escape sequences from JSON
            content = content.replace("\\n", "\n").replace("\\t", "\t").replace("\\\"", "\"");
            Files.createDirectories(Paths.get(path).getParent());
            Files.write(Paths.get(path), content.getBytes(StandardCharsets.UTF_8));
            return "File written: " + path + " (" + content.length() + " bytes)";
        } catch (IOException e) {
            return "Error writing file: " + e.getMessage();
        }
    }

    private String editFile(String path, String oldStr, String newStr) {
        try {
            // Unescape
            oldStr = oldStr.replace("\\n", "\n").replace("\\t", "\t");
            newStr = newStr.replace("\\n", "\n").replace("\\t", "\t");
            String content = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
            if (!content.contains(oldStr)) {
                return "Error: old_string not found in file";
            }
            content = content.replace(oldStr, newStr);
            Files.write(Paths.get(path), content.getBytes(StandardCharsets.UTF_8));
            return "File edited: " + path;
        } catch (IOException e) {
            return "Error editing file: " + e.getMessage();
        }
    }

    private String runCommand(String command) {
        try {
            ProcessBuilder pb = new ProcessBuilder();
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                pb.command("cmd.exe", "/c", command);
            } else {
                pb.command("bash", "-c", command);
            }
            pb.directory(new File(WORKSPACE_ROOT));
            pb.redirectErrorStream(true);

            Process proc = pb.start();
            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {
                String line;
                int lines = 0;
                while ((line = reader.readLine()) != null && lines < 500) {
                    output.append(line).append("\n");
                    lines++;
                }
            }

            boolean finished = proc.waitFor(60, TimeUnit.SECONDS);
            if (!finished) {
                proc.destroyForcibly();
                return output + "\n[Command timed out after 60s]";
            }
            String exitInfo = proc.exitValue() == 0 ? "" : "\n[Exit code: " + proc.exitValue() + "]";
            return output.toString() + exitInfo;
        } catch (Exception e) {
            return "Error running command: " + e.getMessage();
        }
    }

    private String globSearch(String pattern, String basePath) {
        try {
            PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
            StringBuilder sb = new StringBuilder();
            Path start = Paths.get(basePath);
            int maxDepth = 20;
            final int[] count = {0};

            Files.walkFileTree(start, EnumSet.noneOf(FileVisitOption.class), maxDepth, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (count[0] >= 100) return FileVisitResult.TERMINATE;
                    Path relative = start.relativize(file);
                    if (matcher.matches(relative) || matcher.matches(file.getFileName())) {
                        sb.append(relative.toString()).append("\n");
                        count[0]++;
                    }
                    return FileVisitResult.CONTINUE;
                }
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    if (dir.getFileName() != null && (dir.getFileName().toString().startsWith(".")
                            || dir.getFileName().toString().equals("node_modules")
                            || dir.getFileName().toString().equals("target")))
                        return FileVisitResult.SKIP_SUBTREE;
                    if (start.relativize(dir).toString().length() > 200) return FileVisitResult.SKIP_SUBTREE;
                    return FileVisitResult.CONTINUE;
                }
            });
            if (count[0] == 0) return "No files matched pattern: " + pattern;
            if (count[0] >= 100) sb.append("... (results truncated at 100)");
            return sb.toString();
        } catch (IOException e) {
            return "Error searching files: " + e.getMessage();
        }
    }

    private String grepSearch(String pattern, String basePath) {
        try {
            final Pattern regex = compilePattern(pattern);
            StringBuilder sb = new StringBuilder();
            Path start = Paths.get(basePath);
            final int[] totalMatches = {0};
            final int[] fileCount = {0};

            Files.walkFileTree(start, EnumSet.noneOf(FileVisitOption.class), 15, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (totalMatches[0] >= 200 || fileCount[0] >= 50) return FileVisitResult.TERMINATE;
                    String name = file.getFileName().toString();
                    // Skip binary and large files
                    if (name.endsWith(".class") || name.endsWith(".jar") || name.endsWith(".exe")
                            || name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".zip"))
                        return FileVisitResult.CONTINUE;
                    try {
                        if (Files.size(file) > 1024 * 1024) return FileVisitResult.CONTINUE; // Skip >1MB
                        List<String> lines = Files.readAllLines(file);
                        final boolean[] fileHasMatch = {false};
                        for (int i = 0; i < lines.size() && totalMatches[0] < 200; i++) {
                            if (regex.matcher(lines.get(i)).find()) {
                                if (!fileHasMatch[0]) {
                                    sb.append("\n").append(start.relativize(file)).append(":\n");
                                    fileHasMatch[0] = true;
                                    fileCount[0]++;
                                }
                                sb.append(String.format("  %d: %s%n", i + 1, lines.get(i).trim()));
                                totalMatches[0]++;
                            }
                        }
                    } catch (IOException ignored) {}
                    return FileVisitResult.CONTINUE;
                }
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    String dn = dir.getFileName() != null ? dir.getFileName().toString() : "";
                    if (dn.startsWith(".") || dn.equals("node_modules") || dn.equals("target")
                            || dn.equals(".git"))
                        return FileVisitResult.SKIP_SUBTREE;
                    return FileVisitResult.CONTINUE;
                }
            });
            if (totalMatches[0] == 0) return "No matches for: " + pattern;
            if (totalMatches[0] >= 200) sb.append("\n... (results truncated at 200 matches)");
            return sb.toString();
        } catch (IOException e) {
            return "Error searching content: " + e.getMessage();
        }
    }

    private String webSearch(String query) {
        try {
            String encoded = URLEncoder.encode(query, "UTF-8");
            // Use DuckDuckGo Lite for minimal HTML
            String url = "https://lite.duckduckgo.com/lite/?q=" + encoded;
            String html = fetchUrl(url, 5000);
            if (html == null) return "Search failed: could not reach search engine";

            // Simple extraction of result titles and links
            StringBuilder sb = new StringBuilder("Search results for: " + query + "\n\n");
            // Parse DDG lite results: look for <a rel="nofollow" href="...">text</a>
            java.util.regex.Matcher m = java.util.regex.Pattern.compile(
                    "<a[^>]*href=\"([^\"]+)\"[^>]*>([^<]+)</a>").matcher(html);
            int count = 0;
            while (m.find() && count < 10) {
                String link = m.group(1);
                String title = m.group(2).trim();
                if (link.startsWith("http") && !link.contains("duckduckgo") && title.length() > 5) {
                    sb.append(count + 1).append(". ").append(title).append("\n");
                    sb.append("   ").append(link).append("\n\n");
                    count++;
                }
            }
            if (count == 0) return "No results found for: " + query;
            return sb.toString();
        } catch (Exception e) {
            return "Search error: " + e.getMessage();
        }
    }

    private String webFetch(String urlStr) {
        try {
            if (!urlStr.startsWith("http://") && !urlStr.startsWith("https://")) {
                urlStr = "https://" + urlStr;
            }
            String html = fetchUrl(urlStr, 30000);
            if (html == null) return "Failed to fetch: " + urlStr;

            // Basic HTML-to-text: strip tags
            String text = html.replaceAll("<script[^>]*>[\\s\\S]*?</script>", "")
                    .replaceAll("<style[^>]*>[\\s\\S]*?</style>", "")
                    .replaceAll("<br\\s*/?>", "\n")
                    .replaceAll("<[^>]+>", " ")
                    .replaceAll("&amp;", "&")
                    .replaceAll("&lt;", "<")
                    .replaceAll("&gt;", ">")
                    .replaceAll("&quot;", "\"")
                    .replaceAll("&#39;", "'")
                    .replaceAll("\\s+", " ")
                    .replaceAll("\\n\\s*\\n", "\n\n")
                    .trim();

            if (text.length() > 100000) {
                text = text.substring(0, 100000) + "\n\n... [truncated at 100KB]";
            }
            return text;
        } catch (Exception e) {
            return "Fetch error: " + e.getMessage();
        }
    }

    private String listDirectory(String path) {
        File dir = new File(path);
        if (!dir.exists()) return "Path not found: " + path;
        if (!dir.isDirectory()) return "Not a directory: " + path;
        File[] files = dir.listFiles();
        if (files == null) return "Cannot list: " + path;

        StringBuilder sb = new StringBuilder();
        sb.append(dir.getAbsolutePath()).append("/\n");
        // Directories first, then files
        Arrays.sort(files, (a, b) -> {
            if (a.isDirectory() && !b.isDirectory()) return -1;
            if (!a.isDirectory() && b.isDirectory()) return 1;
            return a.getName().compareToIgnoreCase(b.getName());
        });
        for (File f : files) {
            String type = f.isDirectory() ? "/" : "";
            String size = f.isFile() ? readableFileSize(f.length()) : "";
            sb.append(String.format("  %-40s %s%n", f.getName() + type, size));
        }
        return sb.toString();
    }

    // ==================== HELPERS ====================

    /**
     * Parse comma-separated arguments.
     * Handles simple comma-separation. First arg is position 0.
     */
    private String[] parseArgs(String args, int count) {
        String[] result = new String[count];
        if (args == null || args.trim().isEmpty()) return result;
        // Smart split: respect quotes
        List<String> parts = smartSplit(args);
        for (int i = 0; i < Math.min(parts.size(), count); i++) {
            result[i] = parts.get(i).trim();
        }
        return result;
    }

    private List<String> smartSplit(String input) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder current = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        result.add(current.toString());
        return result;
    }

    private String resolvePath(String path) {
        if (path == null || path.trim().isEmpty()) return WORKSPACE_ROOT;
        Path p = Paths.get(path);
        if (p.isAbsolute()) return p.normalize().toString();
        return Paths.get(WORKSPACE_ROOT, path).normalize().toString();
    }

    private String fetchUrl(String urlStr, int timeoutMs) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(timeoutMs);
            conn.setReadTimeout(timeoutMs);
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) Nezha/0.5");
            conn.setInstanceFollowRedirects(true);

            int status = conn.getResponseCode();
            InputStream is = status >= 400 ? conn.getErrorStream() : conn.getInputStream();
            if (is == null) return null;

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[8192];
            int n;
            int maxBytes = 500 * 1024; // 500KB max
            while ((n = is.read(buf)) != -1 && bos.size() < maxBytes) {
                bos.write(buf, 0, n);
            }
            is.close();

            return new String(bos.toByteArray(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }

    private Pattern compilePattern(String pattern) {
        try {
            return Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        } catch (Exception e) {
            return Pattern.compile(Pattern.quote(pattern), Pattern.CASE_INSENSITIVE);
        }
    }

    private String readableFileSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return String.format("%.1f KB", bytes / 1024.0);
        return String.format("%.1f MB", bytes / (1024.0 * 1024.0));
    }
}
