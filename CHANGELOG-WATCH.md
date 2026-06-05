# 🌍 开源智能体工具每日扫描报告

**日期**：2026-06-04
**来源**：GitHub Trending + 重点仓库

---

## 🔥 今日热门项目（GitHub Trending）

### ⭐ 重点关注（与Agent相关）

| 项目 | ⭐ Stars | 今日增量 | 简介 | 可借鉴点 |
|------|---------|---------|------|---------|
| **[NousResearch/hermes-agent](https://github.com/NousResearch/hermes-agent)** | 热门 | Trending | 自我改进AI Agent，内置学习循环。技能系统+跨平台消息网关+定时任务+子Agent并行 | 🔴 **技能系统**：Agent自动从复杂任务创建技能并自我改进；**跨平台网关**：Telegram/Discord/Slack/WhatsApp/Signal统一接入；**Cron调度**：内置定时任务；**子Agent并行**：隔离子Agent做并行工作流 |
| **[supermemoryai/supermemory](https://github.com/supermemoryai/supermemory)** | 25.2K | +600 | AI记忆引擎，自动学习对话提取事实，跨会话记忆，用户画像 | 🔴 **记忆系统**：自动提取对话事实、处理时序变化和矛盾、自动遗忘过期信息；**用户画像**：~50ms获取用户偏好+最近活动；**混合搜索**：RAG+Memory统一查询 |
| **[affaan-m/ECC](https://github.com/affaan-m/ECC)** | 182K+ | 热门 | Agent性能优化系统：技能、本能、记忆、安全、研究驱动开发。支持多Agent工具 | 🟡 **记忆持久化**：Hooks自动保存/加载跨会话上下文；**持续学习**：从会话自动提取模式为可复用技能；**子Agent编排**：上下文问题+迭代检索模式 |
| **[chopratejas/headroom](https://github.com/chopratejas/headroom)** | 10K | +3,530 | AI上下文压缩层，减少60-95% token | 🟡 **Token优化**：JSON/代码/文本自动压缩；**可逆压缩**：原文不删，LLM按需检索；**MCP Server**：可直接集成 |
| **[mnfst/manifest](https://github.com/mnfst/manifest)** | 6.7K | +13 | Agent智能模型路由，降低70%成本 | 🟢 **模型路由**：根据任务复杂度自动选择便宜/贵模型 |

### 🟢 其他值得关注

| 项目 | 简介 | 关联度 |
|------|------|--------|
| **[Scrapling](https://github.com/D4Vinci/Scrapling)** | 自适应Web爬虫框架 | Agent网络搜索工具可参考 |
| **[Open-LLM-VTuber](https://github.com/Open-LLM-VTuber/Open-LLM-VTuber)** | 本地LLM语音交互+Live2D | Agent语音交互灵感 |
| **[activepieces](https://github.com/activepieces/activepieces)** | AI工作流自动化+400 MCP服务器 | MCP集成参考 |

---

## 📋 对 AgentScope Java 的建议集成清单

### 🔴 高优先级（立即有价值）

| 功能 | 参考项目 | 说明 | 复杂度 |
|------|---------|------|--------|
| **长期记忆系统** | supermemory | 从对话中自动提取事实存数据库，下次对话自动注入相关记忆。替代当前的Buffer记忆 | 中 |
| **技能自学习** | hermes-agent | Agent执行复杂任务后自动生成可复用技能文件 | 高 |
| **Token压缩** | headroom | 在发送给LLM前压缩上下文，减少API费用 | 中 |

### 🟡 中优先级（增强体验）

| 功能 | 参考项目 | 说明 | 复杂度 |
|------|---------|------|--------|
| **定时任务调度** | hermes-agent | Web UI上设置Cron任务，Agent定期执行 | 低 |
| **用户画像** | supermemory | 自动维护用户偏好/项目/活跃度档案 | 中 |
| **模型路由** | manifest | 简单任务用便宜模型，复杂任务用贵模型 | 低 |

### 🟢 低优先级（锦上添花）

| 功能 | 参考项目 | 说明 | 复杂度 |
|------|---------|------|--------|
| **子Agent并行** | hermes-agent / ECC | 复杂任务拆分子任务并行执行 | 高 |
| **消息网关** | hermes-agent | 接入微信/钉钉/Telegram | 高 |
| **MCP Server** | headroom / activepieces | 暴露为MCP Server供其他工具调用 | 中 |

---

## 💡 关键洞察

1. **记忆是最热的方向**：supermemory今天+600星，hermes-agent也内置学习循环。我们当前的Buffer记忆很基础，急需升级。
2. **技能系统正在标准化**：hermes-agent的技能系统+agentskills.io开放标准。我们的@Tool注解可以演进为技能文件。
3. **Token压缩是刚需**：headroom今天+3530星。对于多Agent长对话场景，压缩可以显著降低成本。
4. **跨平台消息网关**是差异化能力——hermes-agent支持5个IM平台+CLI，这是目前Java Agent框架普遍缺乏的。

---

*下次扫描时间：待用户确认（建议每日9:00）*
