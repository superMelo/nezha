# 🤖 AgentScope Java

Java 21 + Spring Boot 实现的多智能体框架，功能对标 Python 版 AgentScope。

## 核心功能

| 功能 | 说明 |
|------|------|
| 🤖 **多Agent** | 定义多个Agent，各自有独立记忆和工具 |
| 🔧 **工具调用** | `@Tool`注解定义工具，AI自动调用 |
| 📊 **Pipeline编排** | 串行/循环/条件/广播 4种编排模式 |
| 🧠 **记忆管理** | Buffer记忆，自动裁剪 |
| 🌐 **多模型支持** | OpenAI / Claude / 通义千问 / DeepSeek |
| 🏗️ **Spring Boot** | 完整Spring Boot集成 |

## 快速开始

### 1. 配置模型

编辑 `src/main/resources/application.yml`，填入你的API Key：

```yaml
agentscope:
  models:
    - name: deepseek
      provider: openai
      api-key: sk-xxx           # 你的API Key
      base-url: https://api.deepseek.com/v1
      model: deepseek-chat
```

支持的环境变量：`OPENAI_API_KEY` / `ANTHROPIC_API_KEY` / `DASHSCOPE_API_KEY` / `DEEPSEEK_API_KEY`

### 2. 编译运行

```powershell
mvn spring-boot:run
```

### 3. 自定义Agent

```java
public class CoderAgent extends BaseAgent {
    
    public CoderAgent() {
        super("程序员", "你是一个Java编程专家");
    }
    
    @Tool(name = "read_file", description = "读取文件内容")
    public String readFile(@Param(name = "path", description = "文件路径") String path) {
        return Files.readString(Path.of(path));
    }
    
    @Tool(name = "write_file", description = "写入文件")
    public String writeFile(@Param(name = "path", description = "文件路径") String path,
                           @Param(name = "content", description = "文件内容") String content) {
        Files.writeString(Path.of(path), content);
        return "OK: " + path;
    }
}
```

### 4. Pipeline编排

```java
// 串行: 程序员写代码 → 测试员测试
Pipeline pipeline = Pipeline.sequential(coder, tester);

// 循环: 程序员写代码 → 测试 → 修复（最多10轮）
Pipeline loop = Pipeline.loop(coder, 
    resp -> resp.getContent().contains("PASS"), 10);

// 条件: 简单问题→快速回复，复杂问题→深度分析
Pipeline router = Pipeline.ifElse(
    msg -> msg.getContent().length() < 50,
    quickAgent, deepAgent
);

// 广播: 多Agent讨论
Pipeline debate = Pipeline.broadcast(optimist, pessimist);

// 执行
Msg result = engine.runPipeline(pipeline, "帮我写一个排序算法");
```

## 架构

```
┌─────────────────────────────────────┐
│         AgentScope Java              │
├─────────────────────────────────────┤
│  Agent (BaseAgent)                  │
│  ├── name, sysPrompt                │
│  ├── reply(msg) → msg               │
│  ├── @Tool 注解方法                 │
│  └── Memory (Buffer记忆)            │
├─────────────────────────────────────┤
│  Pipeline (编排)                     │
│  ├── SequentialPipeline (串行)       │
│  ├── LoopPipeline (循环)            │
│  ├── IfElsePipeline (条件分支)       │
│  └── BroadcastPipeline (广播/讨论)   │
├─────────────────────────────────────┤
│  Model (模型抽象)                    │
│  ├── OpenAICompatibleModel          │
│  │   └── OpenAI/DeepSeek/通义千问   │
│  └── ClaudeModel (Anthropic)         │
├─────────────────────────────────────┤
│  ToolRegistry (工具注册/执行)         │
├─────────────────────────────────────┤
│  AgentScopeEngine (核心引擎)         │
│  └── Spring Boot 自动装配            │
└─────────────────────────────────────┘
```

## 支持的模型

| Provider | 说明 | 配置示例 |
|----------|------|---------|
| `openai` | GPT-4o | 默认 |
| `anthropic` | Claude | 需要独立Model类 |
| `dashscope` | 通义千问 | 兼容OpenAI接口 |
| `openai` | DeepSeek | 兼容OpenAI接口 |
