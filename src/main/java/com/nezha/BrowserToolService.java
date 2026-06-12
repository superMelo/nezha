package com.nezha;

import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Browser tools using headless Chrome/Edge command-line mode.
 * No WebSocket, no Selenium — just ProcessBuilder + CLI flags.
 */
@Service
public class BrowserToolService {

    private final ToolRegistry toolRegistry;
    private final OkHttpClient httpClient;
    private String screenshotDir;

    public BrowserToolService(ToolRegistry toolRegistry) {
        this.toolRegistry = toolRegistry;
        this.httpClient = new OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS).readTimeout(20, TimeUnit.SECONDS)
            .proxy(java.net.Proxy.NO_PROXY).build();
        this.screenshotDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "screenshots";
        try { Files.createDirectories(Paths.get(screenshotDir)); } catch (IOException ignored) {}
        registerTools();
    }

    private void registerTools() {
        toolRegistry.registerTool(
            new ToolRegistry.ToolDefinition("browser_navigate", "Open a URL in browser and return page content.", "browser_navigate", "url"),
            args -> fetchViaBrowser(cleanArg(args), false)
        );
        toolRegistry.registerTool(
            new ToolRegistry.ToolDefinition("browser_screenshot", "Take a screenshot of a URL and save as PNG.", "browser_screenshot", "url"),
            args -> screenshot(cleanArg(args))
        );
        toolRegistry.registerTool(
            new ToolRegistry.ToolDefinition("browser_get_text", "Get text content from a web page using headless browser.", "browser_get_text", "url"),
            args -> fetchViaBrowser(cleanArg(args), true)
        );
        toolRegistry.registerTool(
            new ToolRegistry.ToolDefinition("browser_open", "Open a URL in your visible web browser (not headless).", "browser_open", "url"),
            args -> openVisible(cleanArg(args))
        );
        toolRegistry.registerTool(
            new ToolRegistry.ToolDefinition("web_fetch", "Fetch a web page and return its text content.", "web_fetch", "url"),
            args -> fetchHttp(cleanArg(args))
        );
        toolRegistry.registerTool(
            new ToolRegistry.ToolDefinition("web_search", "Search the web and return results.", "web_search", "query"),
            args -> searchWeb(cleanArg(args))
        );
    }

    // ===================== HEADLESS BROWSER (DOM dump) =====================

    private String fetchViaBrowser(String url, boolean textOnly) {
        if (!url.startsWith("http")) url = "https://" + url;
        try {
            long start = System.currentTimeMillis();
            Process proc = runBrowser("--dump-dom", url);

            String html = readProcessOutput(proc, 300 * 1024);
            boolean ok = waitFor(proc, 30);
            long elapsed = System.currentTimeMillis() - start;

            if (!ok) proc.destroyForcibly();
            if (html.isEmpty()) return "Browser returned empty content for: " + url;

            String result;
            if (textOnly) {
                result = htmlToText(html);
                if (result.length() > 30000) result = result.substring(0, 30000) + "\n...[truncated]";
            } else {
                if (html.length() > 50000) html = html.substring(0, 50000) + "\n...[truncated]";
                result = html;
            }
            return "Content from " + url + " (" + elapsed + "ms, " + html.length() + " bytes):\n\n" + result;
        } catch (Exception e) {
            return "Browser error: " + e.getMessage();
        }
    }

    private String screenshot(String url) {
        if (!url.startsWith("http")) url = "https://" + url;
        try {
            String file = screenshotDir + File.separator + "screenshot_" + System.currentTimeMillis() + ".png";
            Process proc = runBrowser("--screenshot=" + file, url);
            boolean ok = waitFor(proc, 30);
            if (!ok) proc.destroyForcibly();
            File f = new File(file);
            if (f.exists() && f.length() > 0) {
                return "Screenshot saved: " + file + " (" + f.length() + " bytes)";
            }
            return "Screenshot failed for: " + url;
        } catch (Exception e) {
            return "Screenshot error: " + e.getMessage();
        }
    }

    // ===================== VISIBLE BROWSER =====================

    private String openVisible(String url) {
        if (!url.startsWith("http")) url = "https://" + url;
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "start", "", url).start();
            } else if (os.contains("mac")) {
                new ProcessBuilder("open", url).start();
            } else {
                new ProcessBuilder("xdg-open", url).start();
            }
            return "Browser opened: " + url;
        } catch (IOException e) {
            return "Error opening browser: " + e.getMessage();
        }
    }

    // ===================== HTTP FETCH =====================

    private String fetchHttp(String urlStr) {
        if (!urlStr.startsWith("http")) urlStr = "https://" + urlStr;
        try {
            Request req = new Request.Builder().url(urlStr)
                .header("User-Agent", "Mozilla/5.0 Nezha/0.5").build();
            try (Response resp = httpClient.newCall(req).execute()) {
                if (!resp.isSuccessful()) return "HTTP " + resp.code() + " for: " + urlStr;
                String body = resp.body() != null ? resp.body().string() : "";
                if (body.isEmpty()) return "Empty response from: " + urlStr;
                String text = htmlToText(body);
                if (text.length() > 50000) text = text.substring(0, 50000) + "\n...[truncated]";
                return "Content from " + urlStr + " (" + body.length() + " bytes):\n\n" + text;
            }
        } catch (Exception e) {
            return "Fetch error: " + e.getMessage();
        }
    }

    private String searchWeb(String query) {
        try {
            String encoded = java.net.URLEncoder.encode(query, "UTF-8");
            Request req = new Request.Builder()
                .url("https://html.duckduckgo.com/html/?q=" + encoded)
                .header("User-Agent", "Mozilla/5.0 Nezha/0.5").build();
            try (Response resp = httpClient.newCall(req).execute()) {
                if (!resp.isSuccessful()) return "Search failed: HTTP " + resp.code();
                String html = resp.body() != null ? resp.body().string() : "";
                return parseSearchResults(html, query);
            }
        } catch (Exception e) {
            return "Search error: " + e.getMessage();
        }
    }

    private String parseSearchResults(String html, String query) {
        StringBuilder sb = new StringBuilder("Search results for: " + query + "\n\n");
        java.util.regex.Pattern linkPat = java.util.regex.Pattern.compile(
            "<a[^>]*class=\"result__a\"[^>]*href=\"([^\"]+)\"[^>]*>([^<]+)</a>");
        java.util.regex.Matcher m = linkPat.matcher(html);
        int count = 0;
        while (m.find() && count < 8) {
            String link = m.group(1);
            String title = m.group(2).replaceAll("<[^>]+>", "").trim();
            if (!link.contains("duckduckgo") && title.length() > 3) {
                sb.append(++count).append(". ").append(title).append("\n");
                sb.append("   ").append(link).append("\n\n");
            }
        }
        return count == 0 ? "No results for: " + query : sb.toString();
    }

    // ===================== HELPERS =====================

    /** Strip surrounding quotes and whitespace from tool arguments */
    private String cleanArg(String arg) {
        if (arg == null) return null;
        arg = arg.trim();
        if (arg.startsWith("\"") && arg.endsWith("\"")) arg = arg.substring(1, arg.length() - 1);
        if (arg.startsWith("'") && arg.endsWith("'")) arg = arg.substring(1, arg.length() - 1);
        return arg.trim();
    }

    private Process runBrowser(String flag, String url) {
        String exe = findBrowser();
        if (exe == null) throw new RuntimeException("No Chrome/Edge found");
        try {
            List<String> cmd = new ArrayList<>();
            cmd.add(exe);
            cmd.add("--headless=new");
            cmd.add("--no-sandbox");
            cmd.add("--disable-gpu");
            cmd.add("--window-size=1280,800");
            cmd.add("--no-first-run");
            cmd.add("--no-default-browser-check");
            cmd.add(flag);  // --dump-dom or --screenshot
            cmd.add(url);
            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.redirectErrorStream(true);
            return pb.start();
        } catch (IOException e) {
            throw new RuntimeException("Failed to start browser: " + e.getMessage());
        }
    }

    private String readProcessOutput(Process proc, int maxBytes) {
        try {
            InputStream is = proc.getInputStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[8192];
            int n;
            while ((n = is.read(buf)) != -1 && bos.size() < maxBytes) {
                bos.write(buf, 0, n);
            }
            return new String(bos.toByteArray(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return "";
        }
    }

    private boolean waitFor(Process proc, int seconds) {
        try {
            return proc.waitFor(seconds, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    private String findBrowser() {
        String[] paths = {
            "C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe",
            "C:\\Program Files\\Microsoft\\Edge\\Application\\msedge.exe",
            "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe",
            "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe",
        };
        for (String p : paths) if (new File(p).exists()) return p;
        return null;
    }

    private String htmlToText(String html) {
        return html
            .replaceAll("(?is)<script[^>]*>.*?</script>", "")
            .replaceAll("(?is)<style[^>]*>.*?</style>", "")
            .replaceAll("<br\\s*/?>", "\n")
            .replaceAll("</p>|</div>|</h[1-6]>|</li>|</tr>", "\n")
            .replaceAll("<[^>]+>", "")
            .replaceAll("&amp;", "&").replaceAll("&lt;", "<").replaceAll("&gt;", ">")
            .replaceAll("&quot;", "\"").replaceAll("&#39;", "'").replaceAll("&nbsp;", " ")
            .replaceAll("\\n\\s*\\n+", "\n\n").trim();
    }
}
