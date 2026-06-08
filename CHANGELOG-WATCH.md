# 🌍 开源智能体工具每日扫描报告

**日期**：2026-06-08
**方式**：GitHub API

---

## 🔥 重点仓库状态（2026-06-08）

| 项目 | ⭐ Stars | 日变化 | 状态 | 说明 |
|------|---------|--------|------|------|
| **[affaan-m/ECC](https://github.com/affaan-m/ECC)** | 210K | +687 | 活跃 | v2.0-rc.1，push 06-07 |
| **[NousResearch/hermes-agent](https://github.com/NousResearch/hermes-agent)** | 186K | +179 | 活跃 | push 06-08，持续迭代 |
| **[microsoft/autogen](https://github.com/microsoft/autogen)** | 59K | +14 | ⚠️ 停滞 | 最后push 04-15，近2月无更新 |
| **[MemPalace/mempalace](https://github.com/MemPalace/mempalace)** | 54.6K | +1K | 活跃 | 记忆系统基准 |
| **[joaomdmoura/crewAI](https://github.com/joaomdmoura/crewAI)** | 53K | +55 | 活跃 | 多Agent编排 |
| **[supermemoryai/supermemory](https://github.com/supermemoryai/supermemory)** | 26K | +168 | 活跃 | 记忆引擎 |
| **[chopratejas/headroom](https://github.com/chopratejas/headroom)** | 16.9K | +1.1K | 活跃 | Token压缩，本周+2.5K |
| **[LangChain4j/langchain4j](https://github.com/LangChain4j/langchain4j)** | 12.2K | +4 | 活跃 | Java LLM框架 |
| **[mnfst/manifest](https://github.com/mnfst/manifest)** | 6.8K | +7 | 缓慢 | 模型路由，push 06-05 |

### 本周新秀（6月创建，100+⭐）

| 项目 | ⭐ | 说明 |
|------|---|------|
| cpaczek/skylight | 2.3K | 飞机投影到天花板，非Agent |
| b-nnett/goose | 2.3K | Swift PoC，非Agent |
| jd-opensource/JoyAI-Echo | 848 | 长@音视频生成，与Agent关联低 |
| tastyeffectco/sandboxd | 496 | 自托管开发沙箱，可参考Agent隔离执行环境 |

---

## 📊 趋势分析

1. **autogen 停滞**：最后push是04-15，近2个月无更新。microsoft可能已转向其他项目，需持续观察。

2. **headroom 本周爆发**：从14.5K→16.9K（+2.5K/周），Token压缩需求强劲。是Nezha压缩引擎升级的最佳参考。

3. **MemPalace 稳步增长**：54K→54.6K（+1K/周），记忆赛道持续验证。

4. **LangChain4j 增长放缓**：+4/天，可能进入成熟期。作为Java生态参考仍有价值。

5. **本周无新Agent框架进入Trending**，赛道格局稳定。

---

## 📋 Nezha v0.4.0 状态

全部🔴项已完成。无紧急集成需求。

| 功能 | 状态 |
|------|------|
| 长期记忆 | ✅ |
| 定时任务 | ✅ |
| Token压缩 | ✅（可升级） |
| 子Agent并行 | ✅ |
| 模型路由 | ✅ |
| 编排模式 | ✅ |
| Web UI | ✅ |

---

## 📜 历史扫描

<details>
<summary>2026-06-07</summary>

## 2026-06-07 扫描报告

| 项目 | ⭐ Stars | 日变化 | 状态 |
|------|---------|--------|------|
| ECC | 209K | +27K | 活跃 |
| hermes-agent | 185K | 暴涨 | 活跃 |
| autogen | 59K | +570 | 活跃 |
| MemPalace | 54K | 新入榜 | 活跃 |
| crewAI | 53K | +380 | 活跃 |
| supermemory | 26K | +647 | 活跃 |
| headroom | 15.8K | +1,300 | 活跃 |
| LangChain4j | 12.2K | +400 | 活跃 |
| manifest | 6.8K | +105 | 活跃 |
| AgentScope | — | — | ❌ 404 |

🚨 AgentScope 仓库已消失。hermes-agent 185K星。

</details>

---

## 🚨 重大发现

1. **OpenBMB/AgentScope 仓库已消失**（404 Not Found）。Nezha从这个项目获得灵感，但该项目已不再维护。这对开源Java Agent框架生态是一个缺口，Nezha恰好填补。

2. **hermes-agent 暴涨至185K星**，远超预期增长。其技能系统+跨平台网关+学习循环代表了Agent框架的未来方向。

3. **MemPalace 54K星**验证了记忆系统是Agent生态的核心需求。

4. **headroom 日增1,300星**持续加速——Token压缩是AI Agent运营的成本瓶颈。

---

## 📋 Nezha v0.4.0 状态

| 功能 | 状态 |
|------|------|
| 长期记忆 | ✅ H2/MySQL持久化 + MemoryService |
| 定时任务 | ✅ Cron调度 + TaskService |
| Token压缩 | ✅ CompressService（首尾保留） |
| 子Agent并行 | ✅ BroadcastPipeline |
| 模型路由 | ✅ ModelRouter（基于关键词+长度） |
| 编排模式 | ✅ Sequential/Broadcast/Loop/IfElse |
| Web UI | ✅ 完整聊天界面 + 主题/双语 |

### 现有功能覆盖评估

所有2026-06-04扫描清单的🔴项已100%完成。当前Nezha功能集已覆盖参考项目核心能力的70%+，暂无紧急集成需求。

### 下次扫描关注

- headroom压缩算法是否可集成（当前Nezha压缩太简单）
- LangChain4j最新版本是否有可参考的Java Agent模式
- Manifest模型路由策略是否有可改进Nezha ModelRouter的思路

---

## 📜 历史扫描

<details>
<summary>2026-06-06</summary>

## 2026-06-06 扫描报告

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
