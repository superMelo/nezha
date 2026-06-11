package com.nezha;

import org.springframework.stereotype.Service;

import okhttp3.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * Lightweight browser tools: open URLs, fetch content, search, take simple screenshots.
 * Uses built-in Java HTTP + OS browser launch — no heavy dependencies.
 */
@Service
public class BrowserToolService {

    private final ToolRegistry toolRegistry;
    private final OkHttpClient httpClient;

    public BrowserToolService(ToolRegistry toolRegistry) {
        this.toolRegistry = toolRegistry;
        this.httpClient = new OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .proxy(java.net.Proxy.NO_PROXY)
            .followRedirects(true)
            .build();
        registerTools();
    }

    private void registerTools() {
        toolRegistry.registerTool(
            new ToolRegistry.ToolDefinition("browser_open", "Open a URL in your default web browser.", "browser_open", "url"),
            args -> openBrowser(args.trim())
        );
        toolRegistry.registerTool(
            new ToolRegistry.ToolDefinition("web_fetch", "Fetch a web page and return its text content. Use for reading pages.", "web_fetch", "url"),
            args -> fetchPage(args.trim())
        );
        toolRegistry.registerTool(
            new ToolRegistry.ToolDefinition("web_search", "Search the web and return results with titles and URLs.", "web_search", "query"),
            args -> searchWeb(args.trim())
        );
    }

    // ===================== IMPLEMENTATIONS =====================

    private String openBrowser(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) url = "https://" + url;
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

    private String fetchPage(String urlStr) {
        if (!urlStr.startsWith("http://") && !urlStr.startsWith("https://")) urlStr = "https://" + urlStr;
        String html = fetchRaw(urlStr, 15000);
        if (html == null) return "Failed to fetch: " + urlStr;
        String text = htmlToText(html);
        if (text.length() > 50000) text = text.substring(0, 50000) + "\n... [truncated at 50KB]";
        return "Content from " + urlStr + " (" + html.length() + " bytes):\n\n" + text;
    }

    private String searchWeb(String query) {
        try {
            String encoded = java.net.URLEncoder.encode(query, "UTF-8");
            String html = fetchRaw("https://html.duckduckgo.com/html/?q=" + encoded, 15000);
            if (html == null) return "Search failed: could not reach search engine";

            StringBuilder sb = new StringBuilder("Search results for: " + query + "\n\n");
            // Parse DDG HTML results
            Pattern linkPat = Pattern.compile("<a[^>]*class=\"result__a\"[^>]*href=\"([^\"]+)\"[^>]*>([^<]+)</a>");
            Pattern snippetPat = Pattern.compile("<a[^>]*class=\"result__snippet\"[^>]*>([^<]+(?:<[^/][^>]*>[^<]*</[^>]+>)*[^<]*)</a>");

            java.util.regex.Matcher lm = linkPat.matcher(html);
            java.util.regex.Matcher sm = snippetPat.matcher(html);

            int count = 0;
            while (lm.find() && count < 8) {
                String link = lm.group(1);
                String title = lm.group(2).replaceAll("<[^>]+>", "").trim();
                if (!link.contains("duckduckgo") && title.length() > 3) {
                    sb.append(count + 1).append(". **").append(title).append("**\n");
                    sb.append("   ").append(link).append("\n");
                    if (sm.find()) {
                        String snippet = sm.group().replaceAll("<[^>]+>", "").trim();
                        sb.append("   ").append(snippet).append("\n");
                    }
                    sb.append("\n");
                    count++;
                }
            }
            if (count == 0) return "No results found for: " + query;
            return sb.toString();
        } catch (Exception e) {
            return "Search error: " + e.getMessage();
        }
    }

    // ===================== HELPERS =====================

    private String fetchRaw(String urlStr, int timeoutMs) {
        try {
            Request request = new Request.Builder()
                .url(urlStr)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) Nezha/0.5")
                .build();
            try (Response response = httpClient.newCall(request).execute()) {
                ResponseBody body = response.body();
                if (body == null) return null;
                String result = body.string();
                if (result.length() > 300 * 1024) result = result.substring(0, 300 * 1024);
                return result;
            }
        } catch (Exception e) {
            return null;
        }
    }

    private String htmlToText(String html) {
        return html
            .replaceAll("(?is)<script[^>]*>.*?</script>", "")
            .replaceAll("(?is)<style[^>]*>.*?</style>", "")
            .replaceAll("(?is)<head[^>]*>.*?</head>", "")
            .replaceAll("<br\\s*/?>", "\n")
            .replaceAll("</p>|</div>|</h[1-6]>|</li>|</tr>", "\n")
            .replaceAll("<[^>]+>", "")
            .replaceAll("&amp;", "&")
            .replaceAll("&lt;", "<")
            .replaceAll("&gt;", ">")
            .replaceAll("&quot;", "\"")
            .replaceAll("&#39;", "'")
            .replaceAll("&nbsp;", " ")
            .replaceAll("\\n\\s*\\n\\s*\\n+", "\n\n")
            .replaceAll("\\n +", "\n")
            .trim();
    }
}
