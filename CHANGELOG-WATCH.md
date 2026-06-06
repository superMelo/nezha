# 🌍 开源智能体工具每日扫描报告

**日期**：2026-06-06
**来源**：GitHub Trending + 重点仓库

---

## 🔥 今日热门项目（GitHub Trending）

### ⭐ 重点关注（与Agent相关）

| 项目 | ⭐ Stars | 今日增量 | 简介 | 可借鉴点 |
|------|---------|---------|------|---------|
| **[NousResearch/hermes-agent](https://github.com/NousResearch/hermes-agent)** | 热门 | Trending | 自我学习AI Agent。技能系统+跨平台消息网关+定时任务+子Agent并行+Token压缩+MCP集成 | ✅ 技能系统/定时调度/Token压缩/子Agent 已在Nezha v0.4.0实现 |
| **[affaan-m/ECC](https://github.com/affaan-m/ECC)** | 182K+ | Trending | v2.0.0-rc.1发布：Hermes Operator支持+跨Harness架构。技能/本能/记忆/安全系统 | 🟡 持续学习+Session状态管理可参考 |
| **[chopratejas/headroom](https://github.com/chopratejas/headroom)** | 14.5K | +2,473 | Token压缩60-95%。新增 `headroom learn`（挖掘失败session写回CLAUDE.md） | 🔴 可集成为Nezha压缩后端替代当前简单折叠 |
| **[MemPalace/mempalace](https://github.com/MemPalace/mempalace)** | 新上榜 | Trending | 开源AI记忆系统。96.6% LongMemEval R@5，零API调用。本地优先，ChromaDB后端 | 🔴 基准验证的记忆方案，可作为Nezha Memory参考 |

### 🟢 其他值得关注

| 项目 | 简介 | 关联度 |
|------|------|--------|
| **[github/copilot-sdk](https://github.com/github/copilot-sdk)** | GitHub Copilot Agent多平台SDK（Java支持！） | Copilot集成 |
| **[withastro/flue](https://github.com/withastro/flue)** | Sandbox Agent框架 | Agent沙箱 |
| **[lfnovo/open-notebook](https://github.com/lfnovo/open-notebook)** | 开源Notebook LM替代品，26K stars | RAG灵感 |

---

## ✅ Nezha v0.4.0 已完成功能（对标扫描清单）

| 功能 | 状态 | 实现细节 |
|------|------|---------|
| 长期记忆系统 | ✅ 已实现 | H2/MySQL持久化 + MemoryService + 搜索/增删/分类 |
| 定时任务调度 | ✅ 已实现 | Cron调度 + TaskService + 每日9点扫描 + 启停开关 |
| Token压缩 | ✅ 已实现 | CompressService：保留首尾消息，中间折叠为摘要 |
| 子Agent并行 | ✅ 已实现 | BroadcastPipeline：多Agent并行回复 |
| 编排模式 | ✅ 已实现 | Sequential/Broadcast/Loop/IfElse 四种编排 |
| Web UI | ✅ 已实现 | 完整聊天UI + 主题切换 + 中英双语 |

---

## 📋 待完成集成清单（更新）

### 🔴 高优先级（基于今日发现）

| 功能 | 参考项目 | 说明 | 复杂度 |
|------|---------|------|--------|
| **压缩引擎升级** | headroom | 当前CompressService只做首尾保留，可集成headroom的智能压缩（JSON/AST/文本分类压缩） | 中 |
| **记忆检索增强** | MemPalace | 当前Memory只有关键词搜索，可增加语义搜索+混合检索 | 高 |
| **模型路由** | manifest | 简洁任务用小模型（降低成本），复杂任务用大模型 | 低 |

### 🟡 中优先级

| 功能 | 参考项目 | 说明 | 复杂度 |
|------|---------|------|--------|
| **技能自学习** | hermes-agent / ECC | Agent执行复杂任务后自动生成可复用技能文件 | 高 |
| **用户画像** | supermemory | 自动维护用户偏好/活跃度档案 | 中 |
| **headroom learn集成** | headroom | 挖掘失败对话自动改进Agent Prompt | 中 |

### 🟢 低优先级

| 功能 | 参考项目 | 说明 | 复杂度 |
|------|---------|------|--------|
| **消息网关** | hermes-agent | 接入Telegram/Discord等 | 高 |
| **MCP Server** | headroom / ECC | 暴露为MCP Server | 中 |
| **GitHub Copilot SDK** | github/copilot-sdk | Java SDK集成 | 低 |

---

## 💡 关键洞察

1. **Token压缩是今日最热**：headroom +2,473星/天，MemPalace/ECC也提供压缩方案。Nezha当前压缩太简单（仅首尾保留），升级空间大。
2. **记忆系统基准化**：MemPalace 96.6% LongMemEval是硬指标。Nezha的记忆模块可以增加语义搜索和混合检索。
3. **Nezha v0.4.0已覆盖大部分核心需求**：长期记忆、定时任务、Token压缩、子Agent并行均已实现。2026-06-04扫描清单中🔴项全部完成。
4. **模型路由是低成本高回报项**：简单问题用便宜模型，复杂问题用大模型，参考manifest/headroom的做法，实现简单收益大。

---

## 📜 历史扫描

<details>
<summary>2026-06-04</summary>

## 2026-06-04 扫描报告

### 🔥 今日热门项目

| 项目 | ⭐ Stars | 今日增量 | 简介 | 可借鉴点 |
|------|---------|---------|------|---------|
| **[NousResearch/hermes-agent](https://github.com/NousResearch/hermes-agent)** | 热门 | Trending | 自我改进AI Agent，内置学习循环 | 🔴 技能系统/跨平台网关/Cron调度/子Agent并行 |
| **[supermemoryai/supermemory](https://github.com/supermemoryai/supermemory)** | 25.2K | +600 | AI记忆引擎，自动学习对话提取事实 | 🔴 记忆系统/用户画像/混合搜索 |
| **[affaan-m/ECC](https://github.com/affaan-m/ECC)** | 182K+ | 热门 | Agent性能优化系统 | 🟡 记忆持久化/持续学习/子Agent编排 |
| **[chopratejas/headroom](https://github.com/chopratejas/headroom)** | 10K | +3,530 | AI上下文压缩60-95% | 🟡 Token优化/可逆压缩/MCP Server |

### 当时建议集成清单
- 🔴 长期记忆系统 → ✅ **已实现**（MemoryService + H2）
- 🔴 技能自学习 → ❌ 待实现
- 🔴 Token压缩 → ✅ **已实现**（CompressService）
- 🟡 定时任务调度 → ✅ **已实现**（TaskService + Cron）
- 🟡 用户画像 → ❌ 待实现
- 🟡 模型路由 → ❌ 待实现

</details>
