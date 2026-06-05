# Nezha Skill

## Overview
Java 8 + Spring Boot 2.7.18 multi-agent framework (H2 database), pipeline orchestration, Web UI.

## Environment
- **JDK 1.8**: `C:\Program Files\Java\jdk1.8.0_351`
- **Maven 3.9.6**: `D:\apache-maven-3.9.6`
- **Maven mirror**: Aliyun (`D:\apache-maven-3.9.6\conf\settings.xml`)
- **Port**: 8080
- **Project**: `C:\Users\yifan\.qclaw\workspace\nezha\`

## Trigger Words
- start/stop/restart/build Nezha
- check Nezha status
- add feature to Nezha

## Commands

### Start
```powershell
taskkill /F /IM java.exe 2>$null; Start-Sleep 2
$env:JAVA_HOME = "C:\Program Files\Java\jdk1.8.0_351"
$env:PATH = "$env:JAVA_HOME\bin;D:\apache-maven-3.9.6\bin;$env:PATH"
cd C:\Users\yifan\.qclaw\workspace\nezha
Start-Process -FilePath "C:\Program Files\Java\jdk1.8.0_351\bin\java.exe" -ArgumentList "-jar","target\nezha-0.4.0.jar" -WindowStyle Hidden
Start-Sleep 10
try { $r = Invoke-WebRequest -Uri http://localhost:8080 -UseBasicParsing -TimeoutSec 5; Write-Host "OK - Status $($r.StatusCode), Size $($r.Content.Length)" } catch { Write-Host "FAILED: $_" }
```

### Stop
```powershell
taskkill /F /IM java.exe 2>$null; Write-Host "Nezha stopped"
```

### Build (clean + package)
```powershell
taskkill /F /IM java.exe 2>$null; Start-Sleep 2
$env:JAVA_HOME = "C:\Program Files\Java\jdk1.8.0_351"
$env:PATH = "$env:JAVA_HOME\bin;D:\apache-maven-3.9.6\bin;$env:PATH"
cd C:\Users\yifan\.qclaw\workspace\nezha
mvn clean package -DskipTests
```

### Status
```powershell
$proc = Get-Process -Name java -ErrorAction SilentlyContinue
if ($proc) {
    try { $r = Invoke-WebRequest -Uri http://localhost:8080 -UseBasicParsing -TimeoutSec 3; Write-Host "RUNNING PID $($proc.Id) - http://localhost:8080 ($($r.Content.Length)b)" } catch { Write-Host "JAVA running PID $($proc.Id) but HTTP not responding" }
} else { Write-Host "STOPPED" }
```

## Post-Update Self-Check (MANDATORY after any code change)

### Step 1: Build must succeed
```powershell
mvn clean package -DskipTests 2>&1 | Select-String "BUILD SUCCESS"
```

### Step 2: Test every API endpoint
```powershell
$eps = @('/api/agents','/api/models','/api/settings','/api/sessions','/api/pipelines','/api/memory/Assistant','/api/tasks')
foreach ($e in $eps) { try { $r = Invoke-WebRequest "http://localhost:8080$e" -UseBasicParsing -TimeoutSec 5; Write-Host "OK GET $e" } catch { Write-Host "FAIL GET $e" } }
```

### Step 3: Test POST endpoints
```powershell
# Sessions
try { $r = Invoke-WebRequest 'http://localhost:8080/api/sessions' -Method POST -Body '{"agentName":"CheckBot"}' -ContentType 'application/json' -UseBasicParsing -TimeoutSec 5; Write-Host "OK POST /api/sessions" } catch { Write-Host "FAIL POST /api/sessions" }
# Chat
try { $r = Invoke-WebRequest 'http://localhost:8080/api/chat' -Method POST -Body '{"agentName":"Assistant","message":"hi"}' -ContentType 'application/json' -UseBasicParsing -TimeoutSec 30; Write-Host "OK POST /api/chat" } catch { Write-Host "FAIL POST /api/chat" }
# Pipelines
try { $r = Invoke-WebRequest 'http://localhost:8080/api/pipelines' -Method POST -Body '{"name":"check-pipe","type":"sequential","configJson":"{\"agents\":[\"Assistant\"]}"}' -ContentType 'application/json' -UseBasicParsing -TimeoutSec 5; Write-Host "OK POST /api/pipelines" } catch { Write-Host "FAIL POST /api/pipelines" }
```

### Step 4: Verify frontend
```powershell
try { $r = Invoke-WebRequest http://localhost:8080 -UseBasicParsing -TimeoutSec 5; Write-Host "OK PAGE $($r.Content.Length)b" } catch { Write-Host "FAIL PAGE" }
```

## Project Structure (v0.4.0)
```
nezha/
├── pom.xml                              # Java 8, Spring Boot 2.7.18
├── skill/SKILL.md                       # This file
└── src/main/
    ├── java/com/nezha/
    │   ├── NezhaApplication.java        # Entry point
    │   ├── NezhaProperties.java         # Config (models, API keys)
    │   ├── NezhaController.java         # REST API (18 endpoints)
    │   ├── GlobalExceptionHandler.java  # Error debug output
    │   ├── AgentService.java            # Agent management
    │   ├── ChatService.java             # Session + message persistence
    │   ├── MemoryService.java           # Long-term memory (agent_memory table)
    │   ├── TaskService.java             # Cron scheduled tasks
    │   ├── CompressService.java         # Token compression
    │   ├── ModelFactory.java            # LLM model caching + creation
    │   ├── LLMModel.java                # Model interface
    │   ├── OpenAIModel.java             # OpenAI-compatible (DeepSeek/Qwen)
    │   ├── ClaudeModel.java             # Anthropic Claude
    │   ├── Msg.java                     # Message object
    │   ├── BaseAgent.java               # Agent with tool calling
    │   ├── ToolRegistry.java            # Tool annotation registry
    │   ├── Pipeline.java                # Pipeline interface
    │   ├── PipelineContext.java         # Pipeline execution context
    │   ├── PipelineService.java         # Pipeline CRUD + execution
    │   ├── SequentialPipeline.java      # Sequential execution
    │   ├── LoopPipeline.java            # Iterative loop
    │   ├── IfElsePipeline.java          # Conditional branching
    │   ├── BroadcastPipeline.java       # Fan-out to all agents
    │   └── web/WebConfig.java           # Disable static cache
    └── resources/
        ├── application.yml              # Server config + model configs
        ├── schema.sql                   # chat_session, chat_message, agent_config, app_setting
        ├── schema-memory.sql            # agent_memory, scheduled_task, pipeline_config
        └── static/index.html            # Web UI (24KB, SPA)
```

## API Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/agents` | GET | List all agents |
| `/api/agents/custom` | POST | Create custom agent |
| `/api/models` | GET | List configured models |
| `/api/settings` | GET | Get masked API keys |
| `/api/settings` | POST | Save API key(s) |
| `/api/sessions` | GET/POST | List/create sessions |
| `/api/sessions/{id}` | DELETE | Delete session |
| `/api/sessions/{id}/messages` | GET | Get session messages |
| `/api/chat` | POST | Chat (supports agentName + pipelineName) |
| `/api/memory/{agent}` | GET | Agent memories |
| `/api/memory` | POST/DELETE | Add/delete memory |
| `/api/tasks` | GET/POST | List/create scheduled tasks |
| `/api/tasks/{id}` | DELETE | Delete task |
| `/api/tasks/{id}/toggle` | POST | Enable/disable task |
| `/api/pipelines` | GET/POST | List/create pipelines |
| `/api/pipelines/{id}` | DELETE | Delete pipeline |
| `/api/compress/{sessionId}` | POST | Compress session history |
| `/h2-console` | GET | H2 database console |

## Key Anti-Patterns (from debugging sessions)

### H2 Database
- H2 does NOT support: `SELECT CALL IDENTITY()`, `SCOPE_IDENTITY()`, `MERGE INTO...KEY()`
- Correct way to get auto-generated ID: `GeneratedKeyHolder` + `keyHolder.getKeys().get("ID")`
- H2 returns ALL columns from `RETURN_GENERATED_KEYS`, not just the ID

### Java 8 Limitations
- No text blocks, no records, no `var`, no switch expressions, no sealed classes
- Lambda must only reference effectively-final variables (not reassigned params)
- Use anonymous inner `PreparedStatementCreator` class instead of lambda for JDBC

### PowerShell File Encoding
- `Set-Content -Encoding UTF8` adds BOM (`\ufeff`) → breaks Java compiler
- Use `[System.IO.File]::WriteAllText` with `UTF8Encoding($false)` for no-BOM
- Or use Python: `open(file, 'w', encoding='utf-8').write(content)`

### Frontend
- No jQuery available (use vanilla JS `document.getElementById()`)
- All UI text uses `\uXXXX` Unicode escapes inside JS i18n objects
- HTML lang attribute must match default language (`lang="zh"`)
- No external JSON language files (embed in JS to avoid Maven resource encoding issues)

## Web UI Features
- 3 built-in agents + custom agent creation
- Pipeline orchestration: sequential / loop / broadcast / if-else
- Multi-session management
- Dark/light theme toggle
- EN/ZH language switch (default ZH)
- Settings dialog for API keys
- Memory management panel
- Scheduled tasks panel
- `Ctrl+Shift+R` to force-refresh after JAR update

## Model Config
- **deepseek** (default): deepseek-chat at api.deepseek.com/v1
- **openai**: gpt-3.5-turbo at api.openai.com/v1
- **claude**: claude-3-sonnet at api.anthropic.com/v1
- **qwen**: qwen-turbo at dashscope.aliyuncs.com/compatible-mode/v1
- DeepSeek API key is baked in; others read from environment variables or settings UI

## Troubleshooting
- **Port 8080 occupied**: `taskkill /F /IM java.exe`
- **Database corruption**: Delete `nezha/nezha-db.mv.db` and restart
- **Maven clean fails**: Kill java.exe first, JAR may be locked
- **Chinese garbled**: Use `\uXXXX` escapes in JS, never external JSON files
- **Compilation errors after file edits**: Check for BOM (`\ufeff`) and wrong path typos
- **500 errors**: Check GlobalExceptionHandler output for exact cause
