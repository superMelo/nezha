# 🌍 开源智能体工具每日扫描报告

**日期**：2026-07-14
**方式**：GitHub API（拉取 2026-07-13 以来所有 commit）

---

## 🔥 重点仓库状态（2026-07-14）

| 项目 | ⭐ Stars | 较07-13 | 新commits | 状态 |
|------|---------|---------|-----------|------|
| [ECC](https://github.com/affaan-m/ECC) | 229,269 | +316 | 0 | 静默 |
| [hermes-agent](https://github.com/NousResearch/hermes-agent) | 214,275 | +536 | 125 | 活跃 |
| [headroom](https://github.com/chopratejas/headroom) | 58,968 | +224 | 57 | 活跃 |
| [crewAI](https://github.com/joaomdmoura/crewAI) | 55,461 | +67 | 1 | 稳定 |
| [autogen](https://github.com/microsoft/autogen) | 59,713 | +32 | 0 | 静默 |
| [supermemory](https://github.com/supermemoryai/supermemory) | 28,362 | +21 | 0 | 静默 |
| [LangChain4j](https://github.com/LangChain4j/langchain4j) | 12,590 | +9 | 3 | 稳定 |
| [manifest](https://github.com/mnfst/manifest) | 7,252 | +6 | 6 | 活跃 |

---

## 📬 各仓库新增 Commit（2026-07-13 → 2026-07-14）

### NousResearch/hermes-agent（125 commits）⭐ 214,275

> Stars：213,739 → 214,275（+536）

| SHA | 日期 | 说明 |
|-----|------|------|
| `19641fa` | 2026-07-14 | fix(desktop): render reasoning text in the Thinking widget (#63999) |
| `2ccfdb2` | 2026-07-13 | fix(agent): exempt parseable vLLM/LM Studio output-cap errors from compression-d |
| `127f6e1` | 2026-07-13 | fix: exempt output-cap errors from compression-disabled guard |
| `7ef9345` | 2026-07-13 | test: add output-cap retry with compression disabled + fix request-pressure test |
| `7577834` | 2026-07-13 | fix(ci): fail closed when workspace matrix discovery produces empty list |
| `a6857fa` | 2026-07-13 | test(desktop): fix remaining act() warnings in gateway-connecting-overlay |
| `c3aa81b` | 2026-07-13 | feat(ci): load npm workspaces from package.json |
| `c44de99` | 2026-07-13 | test(approval): pin blocking E2E flow to manual mode |
| `6ce160a` | 2026-07-13 | Revert "fix(tests): force manual approval mode in E2E blocking tests" |
| `af7dcea` | 2026-07-13 | fix(context): persist fallback compaction breaker |

**亮点**：Thinking widget 渲染 reasoning 文本（#63999）、Windows Git Bash coreutils PATH 修复、vLLM/LM Studio output-cap errors 豁免压缩。

---

### chopratejas/headroom（57 commits）⭐ 58,968

> Stars：58,744 → 58,968（+224）

| SHA | 日期 | 说明 |
|-----|------|------|
| `52a024d` | 2026-07-13 | fix(proxy): strip [1m] model suffix before upstream forwarding (#2027) |
| `35701ce` | 2026-07-13 | fix(shared_context): don't evict an unrelated entry on an update at capacity (#2 |
| `ecdcf13` | 2026-07-13 | fix(compress): don't mutate the caller's CompressConfig via kwargs (#2134) |
| `af7385a` | 2026-07-13 | fix(paths): reject '.', '..', and NUL as plugin names (#2132) |
| `3c1a5cd` | 2026-07-13 | fix(backends/litellm): drop oversized tool names before Bedrock Converse (#2129) |
| `2976d49` | 2026-07-13 | fix(proxy): preserve sub-path in X-Headroom-Base-Url custom upstream (#2037) (#2 |
| `09d1ef4` | 2026-07-13 | fix(proxy): compress Hermes scoped coding-agent passthrough (#1815) |
| `8870b69` | 2026-07-13 | fix(deps): bump pillow to 12.3.0 and click to 8.4.2 (#2097) |

**亮点**：shared_context eviction 修复（容量满时误驱逐无关条目）、CompressConfig kwargs 副作用修复、plugin 名称 NUL 字符拒绝。

---

### LangChain4j（3 commits）

| SHA | 日期 | 说明 |
|-----|------|------|
| `378b6dd` | 07-13 | fix: preserve precision of long/BigInteger/BigDecimal tool arguments（#5755）|
| `3b94250` | 07-13 | Add `MistralAiBatchChatModel` for Mistral Batch API（#5750）|
| `8cce321` | 07-13 | Skills: Declare shell tool timeout_seconds as integer |

**亮点**：`MistralAiBatchChatModel`——支持 Mistral Batch API，异步批处理 LLM 调用。

---

## Nezha 集成评估

无新高价值 commit → 无代码变更需求 → 无需 `mvn clean package` 重启服务。

---

## 📜 历史扫描

<details>
<summary>2026-07-13</summary>

# 🌍 开源智能体工具每日扫描报告

**日期**：2026-07-13
**方式**：GitHub API（拉取 2026-07-10 以来所有 commit）

---

## 🔥 重点仓库状态（2026-07-13）

| 项目 | ⭐ Stars | 较07-10 | 新commits | 状态 |
|------|---------|---------|-----------|------|
| [ECC](https://github.com/affaan-m/ECC) | 228,953 | +677 | 0 | 静默 |
| [hermes-agent](https://github.com/NousResearch/hermes-agent) | 213,739 | +967 | 169 | 活跃 |
| [headroom](https://github.com/chopratejas/headroom) | 58,744 | +336 | 86 | 活跃 |
| [crewAI](https://github.com/joaomdmoura/crewAI) | 55,394 | +78 | 7 | 活跃 |
| [autogen](https://github.com/microsoft/autogen) | 59,681 | +36 | 0 | 静默 |
| [supermemory](https://github.com/supermemoryai/supermemory) | 28,341 | +30 | 23 | 活跃 |
| [LangChain4j](https://github.com/LangChain4j/langchain4j) | 12,581 | +8 | 2 | 稳定 |
| [manifest](https://github.com/mnfst/manifest) | 7,246 | +12 | 19 | 活跃 |

---

## 📬 各仓库新增 Commit（2026-07-10 → 2026-07-13，3天）

### NousResearch/hermes-agent（169 commits）⭐ 213,739

> Stars：212,772 → 213,739（+967）

**主题**：CRON gateway deadlock 修复、context budget 强制、Fireworks AI provider、model picker 重构、workspace session grouping、smart approvals 升级。

| SHA | 日期 | 说明 |
|-----|------|------|
| `7c19eb8` | 2026-07-13 | docs(dashboard): align approval mode guidance |
| `da6d616` | 2026-07-13 | fix(dashboard): correct approvals.mode select options |
| `0c8bcd3` | 2026-07-12 | fix(approval): allow verifier temp cleanup |
| `51382ac` | 2026-07-12 | fix(skills): bind bundles to exact files and origins |
| `c36f6b7` | 2026-07-12 | fix(skills): install referenced bundle files with scan provenance |
| `b482964` | 2026-07-12 | Merge pull request #63091 from NousResearch/bb/salvage-48591-workspace-binding |
| `0c4aed2` | 2026-07-12 | feat(cli): sessions list --workspace filter + Workspace column |
| `602fe1c` | 2026-07-12 | feat(sessions): workspace_key grouping helper + tests |
| `7c14d2a` | 2026-07-12 | Merge pull request #63086 from NousResearch/bb/salvage-59241-workspace-status |
| `e0a650f` | 2026-07-12 | refactor(desktop): text-only workspace status menu + attribution |
| `5fc08c0` | 2026-07-12 | feat(desktop): add workspace path status action |
| `59686df` | 2026-07-12 | Merge pull request #63081 from NousResearch/bb/salvage-45744-workspace-target |

**核心亮点**：
- 🔴 `#62151` **CRON LLM calls inline**：修复 gateway deadlock，cron 调度中 LLM 调用必须同步执行避免死锁
- 🔴 **Context budget 强制**（4个 fix）：`@` 引用展开长期 bug（AttributeError）、context ref scope 错误、budget 未被尊重
- 🔴 **Fireworks AI as preferred provider**：新增 Fireworks AI 集成
- 🔴 **`/model` picker 重构**：credential availability 集中化、provider boundary 强制、unauthenticated pool 不再误判为已认证
- 🟡 **Sessions workspace grouping**：按 workspace_key 分组，CLI `sessions list --workspace` 过滤
- 🟡 **Smart approvals 默认化**（`#62661`）
- 🟡 **Reasoning effort levels**：新增 max 和 ultra 两个级别（`#62650`）

---

### chopratejas/headroom（86 commits）⭐ 58,744

> Stars：58,408 → 58,744（+336）

**主题**：大规模 proxy 政策解耦（30+ isolate/extracted policies）、provider simulator、semantic cache 语义 key、MCP 检索增强。

| SHA | 日期 | 说明 |
|-----|------|------|
| `d0ecc9a` | 2026-07-12 | fix(memory): track MCP retrieval access (#2065) |
| `112d95b` | 2026-07-12 | feat(proxy): report new-content-relative input savings rate in /stats (#2058) |
| `e164f4f` | 2026-07-12 | style(proxy): format anthropic compression lambda |
| `38306a3` | 2026-07-12 | fix(proxy/gemini): thread savings-profile kwargs into apply() (#1994) |
| `2f53a18` | 2026-07-12 | refactor(proxy): isolate semantic cache key policy (#1964) |
| `2c9eb7c` | 2026-07-11 | feat(simulators): add provider simulator service (#2014) |
| `7f7af66` | 2026-07-11 | feat(observability): add gen_ai.request.model to the compression span (#1667) |
| `ad9d086` | 2026-07-11 | feat(codex): keep wrap routing session-scoped (#1507) |
| `0750bbf` | 2026-07-11 | fix(update): prevent _core.pyd corruption on Windows when proxy is running (#158 |
| `2b09ece` | 2026-07-11 | refactor(proxy): isolate image compression policy (#1958) |

**核心亮点**：
- 🔴 **Provider simulator service**（`#2014`）：新增 provider 模拟服务，支持离线测试
- 🔴 **semantic cache by context hash**（`#2022`）：语义缓存 key 从 query text 改为 context hash，更准确
- 🔴 **MCP retrieval access tracking**（`#2065`）：跟踪 MCP 工具检索访问
- 🟡 **30+ proxy 政策解耦**：output/memory/ccr/cache/rate-limit 各模块策略独立提取，耦合大幅降低
- 🟢 **Windows wheel build**（`#1086`）：新增 win_amd64 CI 构建

---

### supermemoryai/supermemory（23 commits）⭐ 28,341

> Stars：28,311 → 28,341（+30）

| SHA | 日期 | 说明 |
|-----|------|------|
| `2cebe81` | 2026-07-11 | fix(web): bypass auth proxy for local dev (#1213) |
| `19d7f12` | 2026-07-11 | fix(web): use backend URL fallback for direct fetches (#1212) |
| `e5e4e49` | 2026-07-11 | fix(tools): coerce limit/offset in ai-sdk schemas (#1202) |
| `5567e82` | 2026-07-11 | docs(self-hosting): configurable embeddings for Supermemory local (#1210) |
| `453185a` | 2026-07-11 | ci: add concurrency groups to workflows (#1221) |

**亮点**：`listMemories` MCP 工具（#1183）——枚举存储的记忆内容，直接对标 Nezha Auto Memory。

---

### mnfst/manifest（19 commits）⭐ 7,246

> Stars：7,234 → 7,246（+12）

| SHA | 日期 | 说明 |
|-----|------|------|
| `2d243fd` | 2026-07-12 | Version Packages (#2483) |
| `2751786` | 2026-07-12 | fix: UI polish — modal z-index, sidebar hover, playground focus, model picker na |
| `eea0f7d` | 2026-07-12 | Merge pull request #2482 from guillaumegay13/fix/harness-sidebar-instant |
| `970ed63` | 2026-07-12 | fix: refresh harness sidebar after creation |
| `71a3170` | 2026-07-12 | Merge pull request #2480 from guillaumegay13/fix/minimax-subscription-compat |

**无重大功能变更**，主要是 model list 同步（GPT-5.6 family、Claude Sonnet 5）和 UI polish。

---

## 🔴 Nezha 集成评估

| 仓库 | 集成价值 | 评估 |
|------|---------|------|
| hermes-agent `#62151` | 🔥极高 | **Gateway CRON deadlock** — CRON LLM calls 必须同步，Nezha Spring Boot @Scheduled 需自查是否存在同类问题 |
| hermes-agent context budget | 高 | `@` 引用展开 AttributeError，Nezha 消息注入 `@` 语法是否有此 bug？ |
| headroom `#2022` | 高 | **语义缓存 key = context hash** vs query text，Nezha Memory 检索可借鉴 |
| supermemory `listMemories` | 中 | MCP 工具枚举记忆，Nezha Memory 可考虑暴露 list API |
| hermes-agent model picker 重构 | 中 | credential pool boundary，Nezha 多模型切换认证可借鉴 |
| 无需本次集成 | — | 高价值项均需架构级改造，留待手动评估 |

---


</details>


## 📜 历史扫描

<details>
<summary>2026-07-14</summary>

# 🌍 开源智能体工具每日扫描报告

**日期**：2026-07-15
**方式**：GitHub API

---

## 🔥 重点仓库状态（2026-07-15）

| 项目 | ⭐ Stars | 较07-14 | 新commits | 状态 | 说明 |
|------|---------|---------|-----------|------|------|
| [hermes-agent](https://github.com/NousResearch/hermes-agent) | 213,302 | +530 | 0 | 静默 |
| [headroom](https://github.com/chopratejas/headroom) | 58,575 | +167 | 0 | 静默 |
| [ECC](https://github.com/affaan-m/ECC) | 228,614 | +338 | 0 | 静默 |
| [crewAI](https://github.com/joaomdmoura/crewAI) | 55,366 | +50 | 0 | 静默 |
| [autogen](https://github.com/microsoft/autogen) | 59,665 | +20 | 0 | 静默 |
| [supermemory](https://github.com/supermemoryai/supermemory) | 28,322 | +11 | 0 | 静默 |
| [LangChain4j](https://github.com/LangChain4j/langchain4j) | 12,574 | +1 | 0 | 静默 |
| [manifest](https://github.com/mnfst/manifest) | 7,240 | +6 | 0 | 静默 |

> 所有 repo 自 07-14 以来无新 commit，可能为周末或 GitHub API 数据缓存延迟
> Stars 为 GitHub 实时数值，较 07-14 列为增量

## 📬 各仓库新增 Commit

**2026-07-14 → 2026-07-15**：无新 commit（静默期）

## Nezha 集成评估

无新高价值 commit → 无代码变更需求 → 无需 `mvn clean package` 重启服务。

---


</details>


## 📜 历史扫描

<details>
<summary>2026-07-14</summary>

# 🌍 开源智能体工具每日扫描报告

**日期**：2026-07-14
**方式**：GitHub API

---

## 🔥 重点仓库状态（2026-07-14）

| 项目 | ⭐ Stars | 较07-10 | 4天增量 | 状态 | 说明 |
|------|---------|---------|--------|------|------|
| **[affaan-m/ECC](https://github.com/affaan-m/ECC)** | 228.3K | +0.4K | +0.4K | 稳定 | 无新 commit |
| **[NousResearch/hermes-agent](https://github.com/NousResearch/hermes-agent)** | 212.8K | +0.6K | +0.6K | 活跃 | 36 commits（桌面沙箱、安全暴露、MTP加速） |
| **[chopratejas/headroom](https://github.com/chopratejas/headroom)** | 58.4K | +0.2K | +0.2K | 🔥 | 16 commits（大量 proxy/CCR 重构） |
| **[joaomdmoura/crewAI](https://github.com/joaomdmoura/crewAI)** | 55.3K | +0.0K | +0.0K | 🔥 | 6 commits（token 使用埋点、CLI TUI） |
| **[microsoft/autogen](https://github.com/microsoft/autogen)** | 59.6K | +0.0K | +0.0K | ❌ | 7月无 commit |
| **[supermemoryai/supermemory](https://github.com/supermemoryai/supermemory)** | 28.3K | +0.0K | +0.0K | 稳定 | 5 commits（CI 优化） |
| **[LangChain4j/langchain4j](https://github.com/LangChain4j/langchain4j)** | 12.6K | +0.0K | +0.0K | 稳定 | 2 commits（Guardrail、Agent 异常包装） |
| **[mnfst/manifest](https://github.com/mnfst/manifest)** | 7.2K | +0.0K | +0.0K | 🔥 | 4 commits（版本发布 + 模型错误修复） |

> Stars 为 GitHub 实时数值；较07-10 列为本次扫描与上次记录之差

---

## 📬 各仓库新增 Commit（2026-07-10 → 2026-07-14）

### NousResearch/hermes-agent（36 commits）⭐ 212,772

> 基线：上次 212.2K → 本次 212,772（+572，增速 ~144⭐/天）

| SHA | 时间 | 类型 | 说明 |
|-----|------|------|------|
| `5e84994` | 07-10 22:51 | feat | **isolated sandbox script for local dev**（桌面沙箱隔离脚本） |
| `b9b463f` | 07-10 14:58 | feat | **expose deterministic tool output risk**（安全：暴露确定性工具输出风险） |
| `dfdc315` | 07-10 12:25 | fix | **remove unavailable OpenCode Zen free models**（#61163） |
| `3aaf7e3` | 07-10 10:42 | feat | **TikTok-style vibe hearts particle system**（桌面情感反馈动画） |
| `fbefb5c` | 07-10 10:42 | refactor | drive vibe heart from core reaction event |
| `0e2adf9` | 07-10 10:42 | feat | **emit + consume reaction signal**（gateway/cli） |
| `9b72995` | 07-10 14:28 | fix | **release pool FDs on owning-thread client close**（#61979） |
| `1a2f3ae` | 07-10 14:47 | fix | tui finalize persist drop conversation_history |
| `97e9c64` | 07-10 13:02 | fix | preserve resolved fork metadata（runtime） |
| `623165a` | 07-10 12:39 | fix | **discover MCP tools in slash workers** |
| `ca65135` | 07-10 12:20 | fix | recover runtime Nous token from shared store |
| `0e67c72` | 07-10 12:20 | fix | validate and persist shared Nous routing |
| `03b8a00` | 07-10 12:20 | fix | recompute Nous routing after shared recovery |
| `8fa0d8b` | 07-10 12:20 | test | assert runtime routing persistence on failure |
| `8727e67` | 07-10 13:20 | fix | preserve resolved fork metadata |
| `de33c24` | 07-10 13:44 | fix | **harden extract input and display boundaries** |
| `7ae9fae` | 07-10 13:44 | fix | handle dict URLs in web_extract |
| `c2a40b2` | 07-10 13:44 | fix | preserve extract result input order |
| `e640bb5` | 07-10 13:44 | test | cover model-facing dict URL dispatch |
| `54e1864` | 07-10 13:44 | fix | unwrap web extract object titles |
| `97fb9e1` | 07-10 13:58 | fix | classify PTB heartbeat transport errors |
| `90bd5b0` | 07-10 13:58 | test | mirror PTB errors in heartbeat recovery |
| `9b72995` | 07-10 14:28 | fix | never stale-remove a one-shot whose run is still alive（cron） |
| `cd7a8df` | 07-10 14:28 | fix | release pool FDs on owning-thread client close |
| `35d777d` | 07-10 14:47 | chore | map WilsonKinyua release attribution |
| `b8880f1` | 07-10 18:06 | fix | type-check electron/ in CI |
| `db8772a` | 07-10 18:06 | fix | don't emit js files when building desktop |
| `f7c9feb` | 07-10 20:14 | fix | only show slash popover when / is first char |
| `fc977f6` | 07-10 21:05 | fix | remove old .js files |
| `1fa3886` | 07-10 21:09 | chore | remove DEV Shift+H heart preview |
| `291eae6` | 07-10 21:14 | merge | bb/desktop-vibe-hearts |
| ...（其余 6 commits） |

**亮点**：
- 🔴 `5e84994` **isolated sandbox script** — 本地开发用隔离沙箱脚本，提升 dev 安全性
- 🔴 `dfdc315` 移除不可用的 OpenCode Zen 免费模型（#61163）
- 🔴 `b9b463f` 安全特性：暴露确定性工具输出风险（#61793）
- 🟡 MCP 工具发现扩展（`623165a`）
- 🟡 Nous token 路由恢复链路（3 个相关 fix）

---

### chopratejas/headroom（16 commits）⭐ 58,408

> 基线：上次 58.2K → 本次 58,408（+208，增速 ~52⭐/天）

**核心趋势**：大量 `isolate` 重构——将 proxy/CCR/cache/output 等模块的策略逻辑拆分为独立函数，每个职责单一，减少耦合。

| SHA | 时间 | 类型 | 说明 |
|-----|------|------|------|
| `9bacf48` | 07-11 00:28 | refactor | **isolate mixed content parsing**（#1939） |
| `5a7265d` | 07-11 00:27 | refactor | isolate auth classification policy（#1945） |
| `b5aa8a3` | 07-11 00:21 | refactor | isolate compression strategy outcomes（#1938） |
| `41af39d` | 07-10 23:17 | fix | **preserve terminal tool on Codex Responses**（#2000） |
| `75d7861` | 07-10 23:12 | fix | cache_savings_usd zeros when litellm unavailable（#2005） |
| `cb38f79` | 07-10 22:41 | refactor | isolate forwarded header policy（#1942） |
| `0ce09fb` | 07-10 22:39 | refactor | isolate verbosity steering（#1940） |
| `fd5b9e7` | 07-10 22:36 | refactor | isolate tool call classification（#1937） |
| `4210d6e` | 07-10 22:35 | refactor | isolate litellm model resolution（#1936） |
| `1f3696a` | 07-10 22:27 | refactor | isolate body forwarding policy（#1935） |
| `d2170b1` | 07-10 18:15 | fix | parse fenced JSON even with prose preamble（#1988） |
| `5e14b8c` | 07-10 15:47 | fix | **don't clobber memories sharing a first line**（#1976） |
| `4cb33cd` | 07-10 15:45 | fix | strip inbound Content-Encoding on messages forward（#1970） |
| `10e4829` | 07-10 15:38 | fix | **shorter fold pointer + MIN_LINES=3, dedup Anthropic list**（#1932） |
| `7dbb9c3` | 07-10 13:44 | chore | **extract agent-evals into standalone headroom-bench repo**（#1967） |
| `88e41b6` | 07-10 13:43 | chore | extract request log redaction policy（#1968） |

**亮点**：
- 🔴 `5e14b8c` **记忆防覆盖**（#1976）：不再用第一行作为 key 覆盖已有记忆，避免不同会话同一主题记忆互相覆盖
- 🔴 `10e4829` dedup 优化（#1932）：折叠指针更短 + 最小行数=3
- 🟡 `41af39d` Codex Responses 终端工具保留（#2000）
- 🟢 `7dbb9c3` 将 benchmark 工具拆出独立仓库 `headroom-bench`

---

### joaomdmoura/crewAI（6 commits）⭐ 55,316

| SHA | 时间 | 类型 | 说明 |
|-----|------|------|------|
| `4fdb7f2` | 07-11 00:37 | fix! | **make tool-result caching opt-in**（#6509，breaking change） |
| `bfa652a` | 07-10 23:33 | fix | stop rewriting authored tool description at construction（#6508） |
| `a8b3ecb` | 07-10 23:29 | fix | expose token usage under both names on results（#6507） |
| `a8b3ecb` | 07-10 21:17 | fix | report per-call usage metrics on kickoff results（#6506） |
| `7967b19` | 07-10 18:44 | fix | stop replaying previous turn's intent when route_turn() falsy |
| `85c467d` | 07-10 14:42 | feat | **run declarative flows on TUI (headless terminal)**（#6484） |

**亮点**：
- 🔴 `4fdb7f2` **工具结果缓存改为 opt-in**（#6509）：之前默认开启，改为需显式启用（可能是性能/一致性原因）
- 🟡 Token 使用量埋点完善（#6506/6507）：kickoff 结果中报告 per-call 用量

---

### supermemoryai/supermemory（5 commits）⭐ 28,311

全为 CI 优化，无功能 commit。

---

### LangChain4j/langchain4j（2 commits）⭐ 12,573

| SHA | 时间 | 说明 |
|-----|------|------|
| `f16130b` | 07-10 21:04 | **add PromptInjectionGuardrail**（#5619） |
| `f7ace00` | 07-10 14:22 | Surface async agent failures as AgentInvocationException（#5746） |

**亮点**：`PromptInjectionGuardrail` — 提示注入防护栅栏，对 LLM 应用安全有直接价值。

---

### mnfst/manifest（4 commits）⭐ 7,234

主要是版本发布 + 模型错误信息优化，无重大功能。

---

## 🔴 Nezha 集成评估

| 仓库 | 集成价值 | 评估 |
|------|---------|------|
| hermes-agent `5e84994` | 高 | 隔离沙箱脚本对安全敏感场景有参考价值，但需针对 Java 重写 |
| hermes-agent `b9b463f` | 高 | 确定性工具输出风险暴露机制，可用于 Nezha 工具审计日志 |
| headroom `5e14b8c` | 🔥高 | **记忆第一行防覆盖**（#1976）：检查 Nezha Memory 是否有同样问题 |
| crewAI `4fdb7f2` | 中 | 工具缓存改为 opt-in，Nezha 可考虑默认关闭工具缓存降低复杂性 |
| LangChain4j `f16130b` | 中 | PromptInjectionGuardrail，Java 实现可引入 Nezha 工具层 |
| 无需本次集成 | — | 最高价值的 #1976 仅是配置级变更，无代码变更需求 |

**待自查**：Nezha Memory 是否存在"同名主题记忆互相覆盖"隐患（与 headroom #1976 同类问题）。

---


</details>


## 📜 历史扫描

<details>
<summary>2026-07-10</summary>

# 🌍 开源智能体工具每日扫描报告

**日期**：2026-07-10
**方式**：GitHub API

---

## 🔥 重点仓库状态（2026-07-10）

| 项目 | ⭐ Stars | 日变化 | 状态 | 说明 |
|------|---------|--------|------|------|
| **[affaan-m/ECC](https://github.com/affaan-m/ECC)** | 227.9K | +0.5K | 活跃 | push 07-09，Devin AI 协助修复 |
| **[NousResearch/hermes-agent](https://github.com/NousResearch/hermes-agent)** | 212.2K | +0.6K | 🔥 | push **今天 00:26**，**修复严重数据丢失 bug** |
| **[chopratejas/headroom](https://github.com/chopratejas/headroom)** | 58.2K | +0.3K | 🔥 | push **今天 00:51**，修复 UnboundLocalError 500 bug |
| **[joaomdmoura/crewAI](https://github.com/joaomdmoura/crewAI)** | 55.3K | +0.1K | 🔥 | push 07-09，内存写 drain 修复、v1.15.2 后继续活跃 |
| **[microsoft/autogen](https://github.com/microsoft/autogen)** | 59.6K | +0.03K | ❌ | 最后push 04-15（**86天**） |
| **[supermemoryai/supermemory](https://github.com/supermemoryai/supermemory)** | 28.3K | +0.03K | 稳定 | push 07-09 |
| **[LangChain4j/langchain4j](https://github.com/LangChain4j/langchain4j)** | 12.6K | +0.01K | 🔥 | push **07-09**，Embedding API 重大重构 |
| **[mnfst/manifest](https://github.com/mnfst/manifest)** | 7.2K | +0.001K | 🔥 | push 07-09，修复健康检查挂起问题 |

### 重点动态

1. **hermes-agent 突破 212K**，今日推送含 **严重数据丢失修复**（#61145）：自动压缩时 
ewrite_transcript() 意外删除已归档的历史消息。
2. **LangChain4j Embedding API 重构**（#5735）：新增请求/响应 API、多模态 embedding（图文混合）、可观测性（Listener）。
3. **manifest** 修复启动时模型注册表加载阻塞健康检查的 bug（可借鉴 Spring Boot 应用启动优化）。
4. **autogen 86 天零更新**，正式死亡。

### 🔴 重点：hermes-agent #61145 数据丢失 bug 修复

| SHA | 消息 | 类型 |
|-----|------|------|
| 79f1274 | chore: AUTHOR_MAP entry for AlexFucuson9 (PR salvage) | 维护 |
| 9cbac64 | test(gateway): pin in-place compaction skipping destructive rewrite | 测试 |
| 549b87c | fix(gateway): prevent hygiene compression from destroying archived transcript (#61145) | **严重修复** |

**Bug 机制**：当自动压缩触发 in-place compaction 时，rchive_and_compact() 已将旧消息软归档（active=0, compacted=1）并存入新 active 消息，但随后 hygiene handler 调用 
ewrite_transcript() → 
eplace_messages(active_only=False) 把包括已归档行的所有记录 **DELETE 掉**，静默数据丢失（用户看不到任何提示）。

**修复**：in-place compaction 成功后跳过 
ewrite_transcript()，只有 legacy session rotation 才调用 rewrite。

**对 Nezha 的参考价值**：
- 检查 Nezha 的 Auto Memory / Token 压缩逻辑是否存在类似隐患：压缩/归档后是否有可能被二次删除？
- 当前 Nezha 压缩逻辑是否依赖 ctive_only 参数？需确认归档消息不会被意外清理
- 列入**高价值待自查项**

### headroom 今日修复（07-10，CI + bugfix）

| SHA | 消息 | 类型 |
|-----|------|------|
| 2d41833 | ci: preserve merge labels while state is unknown | CI |
| 595b709 | ci: keep ready label off changes-requested PRs | CI |
| 1deb947 | fix(proxy): hoist ccr_workspace_key default so /v1/messages survives CCR-inject off (#1096) | **bugfix** |

**#1096 bug**：当用 --no-ccr-inject-tool 且 ccr_proactive_expansion=True（均默认值）时，代码路径中 ccr_workspace_key 在条件块内赋值但在该块外被引用，导致 UnboundLocalError → FastAPI 500 → Claude Code SDK 重试 10 次 → 用户看到 API Error: 500。修复：将默认值 hoist 到条件块外。**这是 headroom 部署级 bug**，影响使用默认配置的用户。

### LangChain4j Embedding API 重大重构（07-09，#5735）

| SHA | 消息 | 类型 |
|-----|------|------|
| 2101288 | EmbeddingModel: request/response API with per-call parameters, multimodal inputs, and observability (#5735) | **重大重构** |

**核心变更**（均为向后兼容 additive）：
- 新 API：EmbeddingModel.embed(EmbeddingRequest) → EmbeddingResponse，与 ChatModel 请求/响应结构对齐
- **多模态 embedding**：EmbeddingInput = 有序 Content 列表（text/image），模态自动检测
- **per-call 参数**：EmbeddingInputType.QUERY / DOCUMENT 区分查询与文档 embedding（对 RAG 效果关键）
- **可观测性**：EmbeddingModelListener（与 ChatModelListener 同形），inline 触发
- **严格 opt-in / fail-fast**：请求用了模型不支持的参数/类型 → UnsupportedFeatureException，不静默忽略
- **支持提供商**：OpenAI、Cohere（多模态）、Voyage（多模态）、Jina（CLIP）、Google Gemini 2（多模态）、Amazon Bedrock Titan
- LangChain4j 的 **agent** 协作者提交了大量代码（gent@langchain4j.dev）

### crewAI 今日（07-09，多 PR 推进）

- #6497：在 Crew 任务完成事件前 **drain 所有内存写**（包括 per-agent memory、manager_agent memory、asyncio.to_thread），避免 telemetry listener 在内存保存完成前就拆除，导致 span orphaning
- #6490：支持自定义 OpenAI 兼容 endpoint 和 legacy base URL 环境变量

### manifest 今日（07-09）

- #2465：修复 ProviderModelRegistryService 启动时同步等待全表扫描（超过 4 分钟），导致平台健康检查超时挂起。改为后台分页加载，期间注册表为空（已有空表处理逻辑），合并时原子写入。

### ECC 今日（07-09）

- #2481：Devin AI 协助修复 — pyproject URLs、Tkinter dashboard 优雅错误处理、1.x→2.0 migration guide
- #2480：reviewer frontmatter retier、data scraper prose

### Nezha 集成评估

- **hermes-agent #61145**：需自查 Nezha Auto Memory 归档逻辑是否存在类似隐患（压缩后消息被二次删除）。高优先级但本次仅为记录，待手动核实。
- **LangChain4j Embedding API**：对 Java 实现的 Nezha 无直接参考（已有 ModelRouter），暂不实现。
- **manifest #2465 启动优化**：对 Spring Boot 的 Nezha 有参考价值（启动时异步加载非关键数据），可列入优化清单。
- **无高价值功能需本次集成** → 未做代码变更 → 无需 mvn clean package 重启。

---


</details>


## 📜 历史扫描

<details>
<summary>2026-07-09</summary>

# 🌍 开源智能体工具每日扫描报告

**日期**：2026-07-09
**方式**：GitHub API

---

## 🔥 重点仓库状态（2026-07-09）

| 项目 | ⭐ Stars | 日变化 | 状态 | 说明 |
|------|---------|--------|------|------|
| **[affaan-m/ECC](https://github.com/affaan-m/ECC)** | 227.4K | +0.4K | 活跃 | push 07-08 |
| **[NousResearch/hermes-agent](https://github.com/NousResearch/hermes-agent)** | 211.6K | +0.6K | 🔥 | push **今天**，桌面 TS 重构 |
| **[chopratejas/headroom](https://github.com/chopratejas/headroom)** | 57.9K | +0.4K | 🔥 | push **今天**，新功能 turn-hook |
| **[joaomdmoura/crewAI](https://github.com/joaomdmoura/crewAI)** | 55.2K | +0.1K | 🔥 | push **今天**，v1.15.2 发布 |
| **[microsoft/autogen](https://github.com/microsoft/autogen)** | 59.6K | +0.03K | ❌ | 最后push 04-15（**85天**） |
| **[supermemoryai/supermemory](https://github.com/supermemoryai/supermemory)** | 28.3K | +0.02K | 稳定 | push 07-07 |
| **[LangChain4j/langchain4j](https://github.com/LangChain4j/langchain4j)** | 12.6K | +0.01K | 稳定 | push 07-08 |
| **[mnfst/manifest](https://github.com/mnfst/manifest)** | 7.2K | +0.001K | 缓慢 | push 07-08 |

### 重点动态

1. **三仓库今天推送** — hermes-agent（00:09）、headroom（00:38）、crewAI（00:25，v1.15.2）。
2. **headroom 57.9K** — 今日推出**新功能** 	urn-hook 扩展点（#1891）：允许扩展插件观察/重写出站工具消息、并可重驱动单次模型回合。这是 proxy 层中间件机制。
3. **crewAI 55.2K** — 发布 **v1.15.2**（#6477/#6479），并修复模型目录缓存（#6468）：按精确 API Key 哈希缓存、永缓存本地 provider（Ollama）、TTL 缩短至 5m。
4. **hermes-agent 211.6K** — 今日 commit 均为桌面端 TypeScript 重构（ts-ify、npm fix），无框架级功能。
5. **autogen 85 天零更新**，彻底死亡。
6. **OpenBMB/AgentScope** — 404 不可访问（第3天），停止追踪。
7. **新信号**：hermes-agent 与 manifest 的 topics 已含 openclaw；manifest 是 hermes 的 LLM 网关/路由伴侣（byok）。

### headroom 今日 commit（07-09，含新功能）

| SHA | 消息 | 类型 |
|-----|------|------|
| ec950f7 | feat(proxy): add turn-hook extension point for buffered model turns (#1891) | **新功能** |
| 3e85eb1 | fix(memory): resolve Trae cwd metadata from user reminders (#1887) | 修复 |

**turn-hook 机制**：TurnHook 协议（on_request / on_response），注册表模式，空注册表时完全 no-op（字节级兼容）。on_response 可调用 call_model 重驱动并返回替换响应——这是 ASGI middleware 做不到的能力。

**对 Nezha 的参考价值**：可在 Nezha 的 Tool 调用框架（ToolDef/ToolRegistry）层设计「模型调用前后钩子」扩展点，但需较大改造，非单 PR 可 drop-in。列入后续参考。

### crewAI 今日 commit（07-09，v1.15.2）

| SHA | 消息 | 类型 |
|-----|------|------|
| 289686a | docs: snapshot and changelog for v1.15.2 (#6479) | 文档 |
| 589baa3 | feat: bump versions to 1.15.2 (#6477) | 发版 |
| 835b93d | fix(cli): key model-catalog cache by exact API key, shorten TTL, skip Ollama (#6468) | 修复 |

**#6468 缓存修复要点**（Nezha ModelRouter 可借鉴）：
- 缓存 Key 用 **精确 API Key 的 sha256 摘要**（绝不存明文 Key），切换账号即 miss 重取
- **本地 provider（Ollama）永不缓存**（随时变更）
- 动态目录 TTL 从 6h 缩至 **5m**

### Nezha 集成评估

- **无高价值功能需立即集成**：headroom turn-hook 是 proxy 层专有机制、crewAI v1.15.2 是增量优化，均非 Nezha（Java Spring Boot）可 drop-in 的能力。
- **可借鉴的优化**（非本次实现）：
  - crewAI 模型目录缓存模式 → 改进 Nezha ModelRouter（哈希 Key、短 TTL、本地 provider 不缓存）
  - headroom turn-hook 概念 → 未来 Nezha 扩展框架的钩子设计
- **待处理遗留问题**（非本次触发）：① send() 创建 session 未传 pipelineName；② session_artifact 表需补充 schema；③ 文件上传 UI 完整性待验证。

---


</details>


## 📜 历史扫描

<details>
<summary>2026-07-08</summary>

# 🌍 开源智能体工具每日扫描报告

**日期**：2026-07-08
**方式**：GitHub API

---

## 🔥 重点仓库状态（2026-07-08）

| 项目 | ⭐ Stars | 日变化 | 状态 | 说明 |
|------|---------|--------|------|------|
| **[affaan-m/ECC](https://github.com/affaan-m/ECC)** | 227.1K | +0.4K | 活跃 | push 07-06 |
| **[NousResearch/hermes-agent](https://github.com/NousResearch/hermes-agent)** | 211.0K | +0.6K | 🔥 | push **今天**，dashboard 多 profile 会话修复 |
| **[chopratejas/headroom](https://github.com/chopratejas/headroom)** | 57.5K | +0.4K | 🔥 | push **今天**，但仅为文档清理 |
| **[joaomdmoura/crewAI](https://github.com/joaomdmoura/crewAI)** | 55.1K | +0.07K | 稳定 | push 07-07 |
| **[microsoft/autogen](https://github.com/microsoft/autogen)** | 59.6K | +0.02K | ❌ | 最后push 04-15（**84天**） |
| **[supermemoryai/supermemory](https://github.com/supermemoryai/supermemory)** | 28.2K | +0.04K | 稳定 | push 07-07 |
| **[LangChain4j/langchain4j](https://github.com/LangChain4j/langchain4j)** | 12.5K | +0.01K | 稳定 | push 07-07 |
| **[mnfst/manifest](https://github.com/mnfst/manifest)** | 7.2K | +0.01K | 缓慢 | push 07-07 |

### 重点动态

1. **两仓库今天推送** — hermes-agent（00:52）、headroom（00:16）。
2. **hermes-agent 211.0K** — 今日 3 commit，全部围绕 dashboard 多 profile 会话管理（model profile scoping、WhatsApp pairing、profile-scoped SessionDB resume）。
3. **headroom 57.5K** — 今日 3 commit **全是文档**（Codex 安装说明、删除已退役 IntelligentContext 文案、修正对比表）。无功能变更，注意官方已确认 **Pipeline 现在只用 live-zone compression**。
4. **autogen 84 天零更新**，彻底死亡。
5. **OpenBMB/AgentScope** — 404 不可访问（仓库已移除/重命名），停止追踪。

### hermes-agent 今日 commit（07-08）

| SHA | 消息 |
|-----|------|
| 4f620a0 | Add WhatsApp dashboard pairing flow |
| 6015ee5 | fix: pass profile-scoped SessionDB to _session_latest_descendant in dashboard chat PTY resume |
| 543f069 | Fix dashboard chat model profile scoping |

**核心趋势**：dashboard 多 profile 会话隔离（model profile scoping / profile-scoped SessionDB）。这与 Nezha 的 pipeline_name 会话编排持久化问题思路一致——**会话应按 profile/pipeline 隔离状态库**，可参考。

### headroom 今日 commit（07-08，文档为主）

| SHA | 消息 |
|-----|------|
| 7721b2 | [codex] docs: add Codex install note (#1757) |
| 9c203dd | [codex] docs: remove retired IntelligentContext copy (#1756) |
| 031055 | docs(readme): correct lean-ctx comparison row (#1754) |

**重要信号**：#1756 明确「Pipeline now uses live-zone compression only」——IntelligentContext / RollingWindow 已退役。headroom 战略收敛到单一 live-zone 压缩算法。

### Nezha 集成评估

- **无高价值功能需立即集成**：今日更新为文档 + dashboard 会话隔离修复，非可落地的框架级能力。
- **可参考思路**：hermes 的 profile-scoped SessionDB → 用于修复 Nezha send() 创建 session 未传 pipelineName 的遗留问题（会话编排持久化不完整）。
- **待处理遗留问题**（非本次触发）：① send() 创建 session 未传 pipelineName；② session_artifact 表需补充 schema；③ 文件上传 UI 完整性待验证。

---


</details>


## 📜 历史扫描

<details>
<summary>2026-07-07</summary>

# 🌍 开源智能体工具每日扫描报告

**日期**：2026-07-07
**方式**：GitHub API

---

## 🔥 重点仓库状态（2026-07-07）

| 项目 | ⭐ Stars | 日变化 | 状态 | 说明 |
|------|---------|--------|------|------|
| **[affaan-m/ECC](https://github.com/affaan-m/ECC)** | 226.7K | +0.4K | 活跃 | push 07-06 |
| **[NousResearch/hermes-agent](https://github.com/NousResearch/hermes-agent)** | 210.4K | +0.7K | 🔥 | push **今天**，model picker 修复 |
| **[chopratejas/headroom](https://github.com/chopratejas/headroom)** | 57.2K | +0.4K | 🔥 | push **今天** |
| **[joaomdmoura/crewAI](https://github.com/joaomdmoura/crewAI)** | 55.0K | +0.03K | 🔥 | push **今天**，模型目录大重构 |
| **[microsoft/autogen](https://github.com/microsoft/autogen)** | 59.5K | 持平 | ❌ | 最后push 04-15（**83天**） |
| **[supermemoryai/supermemory](https://github.com/supermemoryai/supermemory)** | 28.2K | +0.01K | 🔥 | push **今天** |
| **[LangChain4j/langchain4j](https://github.com/LangChain4j/langchain4j)** | 12.5K | +0.03K | 稳定 | push 07-06 |
| **[mnfst/manifest](https://github.com/mnfst/manifest)** | 7.2K | 持平 | 缓慢 | push 07-06 |

### 重点动态

1. **四仓库同一天推送（07-07）** — hermes-agent、headroom、crewAI、supermemory 全部今天 push，极其罕见。
2. **hermes-agent 210.4K** — 今日 5 个 commit，全部围绕 model picker 修复（web/tui 自定义模型探针刷新、-m 全局持久化问题）。
3. **crewAI 55.0K** — mega-PR #6462：模型向导改为动态拉取最新模型，三级回退（vendor API → curated list → LiteLLM），35 个测试。Claude Opus 4.8 主写。
4. **headroom 57.2K** — 今天推送，延续 token 压缩战略。
5. **autogen 83天零更新**，彻底死亡。

### hermes-agent 今日 commit（07-07 UTC 00:37 push）

| SHA | 消息 |
|-----|------|
| 8301654 | fix(web): refresh dashboard model picker |
| 3bee33 | fix(tui): keep bare custom model listing stable |
| 4b4f058 | fix(tui): probe active custom model provider |
| 4131ec3 | fix(tui): support model picker refresh |
| 70c6ae6 | fix(tui): stop hermes --tui -m from persisting the model globally (#59805) |

核心：5 个 fix 全围绕 model picker 自定义模型体验。#59805 是关键 bug：-m CLI 参数意外写入 config.yaml 成为全局默认，已修复为 session 级别。

### crewAI 今日 mega-PR #6462

eat(cli): pull latest LLM models dynamically in the crew wizard

- **问题**：crew wizard 硬编码模型列表，几周就过时
- **方案**：model_catalog.get_provider_models() 三级回退
  1. vendor API（openai/anthropic/gemini/groq/cerebras/ollama 有 key 时）
  2. curated hardcoded list（最新数据：Anthropic Fable 5 / Opus 4.8 / Sonnet 5；OpenAI GPT-5.5；Gemini 3.5 Flash 等）
  3. LiteLLM feed（无 curated 的供应商）
- **亮点**：6h 缓存、按日期/版本排名、humanize 标签、负缓存防止重复拉取失败
- 35 个测试，Claude Opus 4.8 主写

### Nezha 集成参考

- **hermes #59805 修复**：session-scoped 模型切换不写 config.yaml → 值得在 Nezha Pipeline 编排中参考类似隔离机制
- **crewAI 模型动态目录**：Nezha 的模型选择器也可以考虑 vendor API 动态拉取

---


</details>


## 📜 历史扫描

<details>
<summary>2026-07-06</summary>

# 🌍 开源智能体工具每日扫描报告

**日期**：2026-07-06
**方式**：GitHub API

---

## 🔥 重点仓库状态（2026-07-06）

| 项目 | ⭐ Stars | 日变化 | 状态 | 说明 |
|------|---------|--------|------|------|
| **[affaan-m/ECC](https://github.com/affaan-m/ECC)** | 226.3K | +0.3K | 活跃 | push 07-04 |
| **[NousResearch/hermes-agent](https://github.com/NousResearch/hermes-agent)** | 209.7K | +0.5K | 🔥 | push 今天，持续活跃 |
| **[chopratejas/headroom](https://github.com/chopratejas/headroom)** | 56.8K | +0.3K | 稳步 | push 07-05 |
| **[joaomdmoura/crewAI](https://github.com/joaomdmoura/crewAI)** | 55.0K | +0.1K | 活跃 | push 07-05 |
| **[microsoft/autogen](https://github.com/microsoft/autogen)** | 59.5K | +0.02K | ❌ | 最后push 04-15（82天） |
| **[supermemoryai/supermemory](https://github.com/supermemoryai/supermemory)** | 28.2K | 持平 | 活跃 | push 07-05 |
| **[LangChain4j/langchain4j](https://github.com/LangChain4j/langchain4j)** | 12.5K | 持平 | 稳定 | v1.17.1 |
| **[mnfst/manifest](https://github.com/mnfst/manifest)** | 7.2K | 持平 | 缓慢 | push 07-05 |

### 重点动态

1. **hermes-agent 209.7K** — 今天继续推送，延续会话管理修复。连续活跃，200K后增速稳健。
2. **crewAI 55.0K** — 增速恢复，突破55K心理关口。
3. **headroom 56.8K** — 稳步增长，无大版本更新。
4. **autogen 82天零更新**，正式宣告死亡。

---

## 📊 趋势分析

1. **hermes-agent 连续2天推送**，200K俱乐部中最活跃。
2. **crewAI 突破55K**，增速恢复到+0.1K/天。
3. **ECC 226K+**，稳居第一但增速放缓。
4. **无重大技术突破**，赛道进入稳定期。

---

## 📋 Nezha 状态

v0.5.2 稳定，MySQL持久化。

---


</details>


## 📜 历史扫描

<details>
<summary>2026-07-05</summary>

# 🌍 开源智能体工具每日扫描报告

**日期**：2026-07-05
**方式**：GitHub API

---

## 🔥 重点仓库状态（2026-07-05）

| 项目 | ⭐ Stars | 日变化 | 状态 | 说明 |
|------|---------|--------|------|------|
| **[affaan-m/ECC](https://github.com/affaan-m/ECC)** | 226.0K | +0.3K | 活跃 | push 07-04 |
| **[NousResearch/hermes-agent](https://github.com/NousResearch/hermes-agent)** | 209.2K | +0.5K | 🔥 | push **今天**，3个会话管理修复 |
| **[chopratejas/headroom](https://github.com/chopratejas/headroom)** | 56.5K | +0.3K | 稳步 | push 07-03 |
| **[microsoft/autogen](https://github.com/microsoft/autogen)** | 59.5K | 0 | ❌ | 最后push 04-15（**81天**） |
| **[joaomdmoura/crewAI](https://github.com/joaomdmoura/crewAI)** | 54.9K | +0.1K | 活跃 | push 07-04 |
| **[supermemoryai/supermemory](https://github.com/supermemoryai/supermemory)** | 28.2K | +0.02K | 活跃 | push **今天** |
| **[LangChain4j/langchain4j](https://github.com/LangChain4j/langchain4j)** | 12.5K | 持平 | 稳定 | v1.17.1 |
| **[mnfst/manifest](https://github.com/mnfst/manifest)** | 7.2K | 持平 | 缓慢 | push 07-03 |

### 重点动态

1. **hermes-agent 209.2K** — 昨天3个重要修复：
   - ix(gateway): clear last-resolved-model cache on /new — /new命令清模型缓存，避免配置切换后使用旧模型
   - ix(gateway): attach credential_pool to session /model overrides — 会话model覆盖接入credential_pool，支持计费轮换
   - ix(gateway): load display config from routed profile — 从profile加载display配置
   
2. **ECC 226K**，持续增长。

3. **supermemory 今天推送**（内存/上下文引擎）。

4. **autogen 81天零更新**，正式宣告死亡。

---

## 📊 趋势分析

1. **hermes-agent 会话管理持续演进**，/new清缓存、model override接入credential_pool这些修复对Nezha会话系统有参考价值。
2. **ECC稳步向227K迈进**，无重大功能更新。
3. **autogen已死**，81天零更新可从监控列表移除。
4. **headroom 56.5K**，增速放缓但仍稳定。

---

## 📋 Nezha 状态

v0.5.2 稳定，MySQL持久化。

**待集成参考**：
- hermes /new 清模型缓存机制（会话重置时清理状态）
- hermes credential_pool 接入（API Key轮换/计费）

---


</details>


## 📜 历史扫描

<details>
<summary>2026-07-02</summary>

# 🌍 开源智能体工具每日扫描报告

**日期**：2026-07-02
**方式**：GitHub API

---

## 🔥 重点仓库状态（2026-07-02）

| 项目 | ⭐ Stars | 日变化 | 状态 | 说明 |
|------|---------|--------|------|------|
| **[affaan-m/ECC](https://github.com/affaan-m/ECC)** | 224.7K | +0.6K | 活跃 | push 07-01 |
| **[NousResearch/hermes-agent](https://github.com/NousResearch/hermes-agent)** | 207.3K | +0.8K | 活跃 | push 07-02 |
| **[chopratejas/headroom](https://github.com/chopratejas/headroom)** | **55.2K** | +0.7K | 稳步 | push 07-01，transformers 5.3 |
| **[microsoft/autogen](https://github.com/microsoft/autogen)** | 59.4K | 0 | ❌ | 最后push 04-15（48天） |
| **[joaomdmoura/crewAI](https://github.com/joaomdmoura/crewAI)** | 54.7K | +0.1K | 活跃 | push 07-01 |
| **[supermemoryai/supermemory](https://github.com/supermemoryai/supermemory)** | 28.1K | +0.1K | 活跃 | push 07-01 |
| **[LangChain4j/langchain4j](https://github.com/LangChain4j/langchain4j)** | 12.5K | 持平 | 稳定 | v1.17.1（06-30） |
| **[mnfst/manifest](https://github.com/mnfst/manifest)** | 7.2K | +0.05K | 缓慢 | push 07-01 |

### 重点动态

1. **headroom 55.2K** — chore(deps): bump transformers 5.0→5.3 ML依赖升级；ix(install): use Windows-safe PID liveness probe Windows安装健壮性修复。
2. **hermes-agent 207.3K**，稳定+0.8K。
3. **ECC 224.7K**，持续+0.6K/天。
4. **autogen 连续48天零更新**。

---

## 📊 趋势分析

1. **headroom 增速稳定在+0.7K/天**，依赖升级说明在紧跟ML生态（transformers 5.3是最新）。
2. **hermes-agent 207K+**，200K后增速健康。
3. **ECC 225K**，稳居第一。
4. **无重大突破**，赛道平稳。

---

## 📋 Nezha 状态

v0.5.2 稳定，MySQL持久化。

---

## 📜 历史扫描

<details>
<summary>2026-07-01</summary>

# 🌍 开源智能体工具每日扫描报告

**日期**：2026-07-01
**方式**：GitHub API

> 🎉 下半场开局！

---

## 🔥 重点仓库状态（2026-07-01）

| 项目 | ⭐ Stars | 日变化 | 状态 | 说明 |
|------|---------|--------|------|------|
| **[affaan-m/ECC](https://github.com/affaan-m/ECC)** | 224.1K | +0.6K | 🔥 | push 07-01，**破224K** |
| **[NousResearch/hermes-agent](https://github.com/NousResearch/hermes-agent)** | 206.5K | +0.8K | 活跃 | push 07-01 |
| **[chopratejas/headroom](https://github.com/chopratejas/headroom)** | **54.7K** | +0.8K | 🚀 | push 06-30，force-kompress-all |
| **[microsoft/autogen](https://github.com/microsoft/autogen)** | 59.4K | 0 | ❌ | 最后push 04-15（47天） |
| **[joaomdmoura/crewAI](https://github.com/joaomdmoura/crewAI)** | 54.6K | +0.1K | 活跃 | push 06-30 |
| **[supermemoryai/supermemory](https://github.com/supermemoryai/supermemory)** | 28K | +0.1K | 活跃 | push 06-30 |
| **[LangChain4j/langchain4j](https://github.com/LangChain4j/langchain4j)** | 12.5K | +0.05K | 🆕 | **v1.17.1 patch**（06-30） |
| **[mnfst/manifest](https://github.com/mnfst/manifest)** | 7.2K | 0 | 缓慢 | push 06-30 |

### 重点动态

1. **headroom 54.7K** — `feat(proxy): add --force-kompress-all` 全量内容压缩路由，`fix(compression): reject lossy unmarked tool output` 防止工具输出损毁。压缩策略持续演进。
2. **LangChain4j v1.17.1** — v1.17.0 Debate模式的补丁版，修复edge case。
3. **ECC 劲增+0.6K** 破224K，可能是v0.9.0前奏。
4. **hermes 206.5K**，稳定+0.8K。
5. **autogen 47天零更新**。

---

## 📊 趋势分析

1. **7月开局**：headroom持续压缩战略（force-kompress-all），LangChain4j快速迭代。
2. **ECC突然加速** +0.6K（平时+0.3-0.4K），值得关注。
3. **LangChain4j 5天两版**（1.17.0→1.17.1），迭代加速。

---

## 📋 Nezha 状态

v0.5.2 稳定，MySQL持久化。

---


</details>



</details>


## 📜 历史扫描

<details>
<summary>2026-06-30</summary>

## 🔥 重点仓库状态（2026-06-30）

| 项目 | ⭐ Stars | 日变化 | 状态 | 说明 |
|------|---------|--------|------|------|
| **[affaan-m/ECC](https://github.com/affaan-m/ECC)** | 223.5K | +0.4K | 活跃 | push 06-29 |
| **[NousResearch/hermes-agent](https://github.com/NousResearch/hermes-agent)** | 205.7K | +0.7K | 活跃 | push 06-30 |
| **[chopratejas/headroom](https://github.com/chopratejas/headroom)** | **53.9K** | +0.8K | 稳步 | push 06-29，OpenCode原生路由 |
| **[microsoft/autogen](https://github.com/microsoft/autogen)** | 59.4K | 0 | ❌ | 最后push 04-15（46天） |
| **[joaomdmoura/crewAI](https://github.com/joaomdmoura/crewAI)** | 54.6K | +0.1K | 活跃 | push 06-29 |
| **[supermemoryai/supermemory](https://github.com/supermemoryai/supermemory)** | 27.9K | +0.1K | 活跃 | push 06-29 |
| **[LangChain4j/langchain4j](https://github.com/LangChain4j/langchain4j)** | 12.5K | 持平 | 活跃 | v1.17.0（06-26） |
| **[mnfst/manifest](https://github.com/mnfst/manifest)** | 7.2K | +0.05K | 缓慢 | push 06-29 |

### 重点动态

1. **headroom 53.9K** — `fix(opencode): route native providers + load transport plugin` OpenCode 原生提供商路由修复；`chore: release main` 新版本发布。
2. **hermes-agent 205.7K**，稳定+0.7K。
3. **LangChain4j v1.17.0 发布后4天无新版**。

---

## 📊 趋势分析

1. **6月总结**：headroom 从约30K飙至53.9K（+80%），hermes从190K到205.7K（+8%），ECC 219K→223.5K（+2%）。
2. **autogen 46天死**，6月零更新。
3. **LangChain4j 6月发2版**（v1.16.3 + v1.17.0 Debate模式）。
4. **赛道已定型**：headroom（编码Agent）、hermes（部署Agent）、ECC（通用Agent）三足鼎立。

---

## 📋 Nezha 状态

v0.5.2 稳定，MySQL持久化。

---

## 📜 历史扫描

<details>
<summary>2026-06-29</summary>

## 🔥 重点仓库状态（2026-06-29）

| 项目 | ⭐ Stars | 日变化 | 状态 | 说明 |
|------|---------|--------|------|------|
| **[affaan-m/ECC](https://github.com/affaan-m/ECC)** | 223.1K | +0.5K | 活跃 | push 06-25 |
| **[NousResearch/hermes-agent](https://github.com/NousResearch/hermes-agent)** | 205K | +0.6K | 活跃 | push 06-29 |
| **[chopratejas/headroom](https://github.com/chopratejas/headroom)** | **53.1K** | +0.5K | 稳步 | push 06-28，周一恢复 |
| **[microsoft/autogen](https://github.com/microsoft/autogen)** | 59.3K | 0 | ❌ | 最后push 04-15（45天） |
| **[joaomdmoura/crewAI](https://github.com/joaomdmoura/crewAI)** | 54.5K | +0.05K | 活跃 | push 06-28 |
| **[supermemoryai/supermemory](https://github.com/supermemoryai/supermemory)** | 27.9K | +0.1K | 活跃 | push 06-28 |
| **[LangChain4j/langchain4j](https://github.com/LangChain4j/langchain4j)** | 12.5K | +0.05K | 活跃 | v1.17.0（06-26） |
| **[mnfst/manifest](https://github.com/mnfst/manifest)** | 7.2K | +0.05K | 缓慢 | push 06-26 |

### 重点动态

1. **headroom 53.1K** — 周一恢复+0.5K。`fix: recover persistent proxy feature checks` 代理功能恢复；`fix(bedrock): add boto3 1.41 + CRT for aws login credentials` Bedrock支持增强。
2. **hermes-agent 205K**，周一恢复+0.6K。
3. **LangChain4j v1.17.0 稳定中**，无新版本。

---

## 📊 趋势分析

1. **headroom 增速稳定在+0.5K/天**，周末后自然恢复。
2. **hermes-agent 205K+**，200K后增速健康。
3. **ECC 223K**，稳居第一。
4. **无重大突破**，赛道平稳。

---

## 📋 Nezha 状态

v0.5.2 稳定，MySQL持久化。

---

## 📜 历史扫描

<details>
<summary>2026-06-28</summary>

## 🔥 重点仓库状态（2026-06-28）

| 项目 | ⭐ Stars | 日变化 | 状态 | 说明 |
|------|---------|--------|------|------|
| **[affaan-m/ECC](https://github.com/affaan-m/ECC)** | 222.6K | +0.4K | 活跃 | push 06-25 |
| **[NousResearch/hermes-agent](https://github.com/NousResearch/hermes-agent)** | 204.4K | +0.6K | 活跃 | push 06-28 |
| **[chopratejas/headroom](https://github.com/chopratejas/headroom)** | **52.6K** | +0.6K | 放缓 | push 06-28，安全加固+SBOM |
| **[microsoft/autogen](https://github.com/microsoft/autogen)** | 59.3K | 0 | ❌ | 最后push 04-15（44天） |
| **[joaomdmoura/crewAI](https://github.com/joaomdmoura/crewAI)** | 54.5K | +0.1K | 活跃 | push 06-27 |
| **[supermemoryai/supermemory](https://github.com/supermemoryai/supermemory)** | 27.8K | +0.1K | 活跃 | push 06-27 |
| **[LangChain4j/langchain4j](https://github.com/LangChain4j/langchain4j)** | 12.4K | 持平 | 活跃 | v1.17.0（06-26） |
| **[mnfst/manifest](https://github.com/mnfst/manifest)** | 7.1K | 0 | 缓慢 | push 06-26 |

### 重点动态

1. **headroom 52.6K** — 周末增速放缓至+0.6K。安全里程碑：`feat(security): stateless guarantee + model pinning` 无状态保证+模型锁定，`fix(deps): remediate dependency CVEs and publish SBOM` 发布软件物料清单。产品进入企业安全合规阶段。
2. **hermes-agent 204.4K**，周末+0.6K。
3. **LangChain4j v1.17.0 发布后稳定**。

---

## 📊 趋势分析

1. **headroom 安全合规化**：SBOM发布+依赖CVE修复+无状态保证，说明在准备企业级客户。
2. **hermes-agent 204K**，200K后增速健康。
3. **LangChain4j v1.17.0 Debate模式**值得后续跟进。
4. **周末整体活跃度低**。

---

## 📋 Nezha 状态

v0.5.2 稳定，MySQL持久化。

---

## 📜 历史扫描

<details>
<summary>2026-06-27</summary>

## 🔥 重点仓库状态（2026-06-27）

| 项目 | ⭐ Stars | 日变化 | 状态 | 说明 |
|------|---------|--------|------|------|
| **[affaan-m/ECC](https://github.com/affaan-m/ECC)** | 222.2K | +0.4K | 活跃 | push 06-25 |
| **[NousResearch/hermes-agent](https://github.com/NousResearch/hermes-agent)** | 203.8K | +0.7K | 活跃 | push 06-27 |
| **[chopratejas/headroom](https://github.com/chopratejas/headroom)** | **52K** | **+1K** | 🚀 | push 06-27，WebSocket安全加固 |
| **[microsoft/autogen](https://github.com/microsoft/autogen)** | 59.3K | 0 | ❌ | 最后push 04-15（43天） |
| **[joaomdmoura/crewAI](https://github.com/joaomdmoura/crewAI)** | 54.4K | +0.1K | 活跃 | push 06-26 |
| **[supermemoryai/supermemory](https://github.com/supermemoryai/supermemory)** | 27.7K | +0.1K | 活跃 | push 06-27 |
| **[LangChain4j/langchain4j](https://github.com/LangChain4j/langchain4j)** | 12.4K | +0.05K | 🆕 | **v1.17.0 发布**（06-26） |
| **[mnfst/manifest](https://github.com/mnfst/manifest)** | 7.1K | 0 | 缓慢 | push 06-26 |

### 🆕 重点动态

1. **LangChain4j v1.17.0 重磅发布！** 三大新特性：
   - 🔥 **Debate agentic pattern** — 多Agent辩论模式！多个Agent对同一问题独立推理后辩论，选出最优答案。这是Java生态首个辩论式Agent编排。
   - **Tool compensating action** — 工具执行失败时自动触发补偿/回滚操作。
   - **Oracle Database for Chat Memory** — 新增Oracle数据库聊天记忆支持。
   - **Expose unmapped raw streaming events** — 暴露未映射的原始流事件。
2. **headroom 52K**，WebSocket origin安全加固，CCR hash路由守卫。
3. **hermes-agent 203.8K**，稳定+0.7K/天。

---

## 📊 趋势分析

1. **LangChain4j v1.17.0 的 Debate 模式值得关注** — Nezha 目前有 BroadcastPipeline（并行+合并），但 Debate 模式更进一层：多Agent辩论+投票选出最优答案。可作为 Nezha v0.6.0 的新 Pipeline 类型。
2. **Tool compensating action** 模式也值得引入 — Nezha 的 ToolRegistry 可增加失败回调。
3. **headroom 52K**，增长稳定在+1K/天。
4. **autogen 43天死**。

---

## 🔧 待集成清单

| 功能 | 来源 | 优先级 | 状态 |
|------|------|--------|------|
| Debate Pipeline（多Agent辩论） | LangChain4j v1.17.0 | 🟡 中 | 建议 Nezha v0.6.0 |
| Tool 补偿/回滚动作 | LangChain4j v1.17.0 | 🟢 低 | 增强 ToolRegistry |

---

## 📋 Nezha 状态

v0.5.2 稳定，MySQL持久化。

---

## 📜 历史扫描

<details>
<summary>2026-06-26</summary>

## 🔥 重点仓库状态（2026-06-26）

| 项目 | ⭐ Stars | 日变化 | 状态 | 说明 |
|------|---------|--------|------|------|
| **[affaan-m/ECC](https://github.com/affaan-m/ECC)** | 221.8K | +0.6K | 活跃 | push 06-25 |
| **[NousResearch/hermes-agent](https://github.com/NousResearch/hermes-agent)** | 203.1K | +1K | 活跃 | push 06-26 |
| **[chopratejas/headroom](https://github.com/chopratejas/headroom)** | **51K** | **+1.1K** | 🎉50K | push 06-25，**突破50K里程碑** |
| **[microsoft/autogen](https://github.com/microsoft/autogen)** | 59.3K | 0 | ❌ | 最后push 04-15（42天） |
| **[joaomdmoura/crewAI](https://github.com/joaomdmoura/crewAI)** | 54.4K | +0.1K | 活跃 | push 06-26 |
| **[supermemoryai/supermemory](https://github.com/supermemoryai/supermemory)** | 27.5K | +0.1K | 活跃 | push 06-25 |
| **[LangChain4j/langchain4j](https://github.com/LangChain4j/langchain4j)** | 12.4K | +0.05K | 活跃 | v1.16.3（06-18），停滞8天 |
| **[mnfst/manifest](https://github.com/mnfst/manifest)** | 7.1K | 0 | 缓慢 | push 06-25 |

### 重点动态

1. **🎉 headroom 突破50K！** — v0.26.0 Copilot BYOK后的史诗级增长：06-04的30K→06-26的51K，22天涨21K。`feat(wrap): make tokensave the primary coding-task compressor` 将压缩功能升级为默认，`fix(transforms): gate tool string output from lossy compression` 防止工具输出被有损压缩。
2. **hermes-agent 203K**，恢复+1K/天增速。
3. **autogen 连续42天零更新**。

---

## 📊 趋势分析

1. **headroom 22天涨21K**，v0.26.0后的爆发增长未见放缓迹象。tokensave升级为主压缩器说明产品在快速成熟。
2. **hermes-agent 203K**，稳定+1K/天。
3. **ECC 222K**，稳居第一。
4. **LangChain4j 停滞8天**无新版本，可能准备v1.17大版本。

---

## 📋 Nezha 状态

v0.5.2 稳定，MySQL持久化。

---

## 📜 历史扫描

<details>
<summary>2026-06-25</summary>

## 🔥 重点仓库状态（2026-06-25）

| 项目 | ⭐ Stars | 日变化 | 状态 | 说明 |
|------|---------|--------|------|------|
| **[affaan-m/ECC](https://github.com/affaan-m/ECC)** | 221.2K | +0.3K | 活跃 | push 06-22 |
| **[NousResearch/hermes-agent](https://github.com/NousResearch/hermes-agent)** | 202.1K | +0.5K | 活跃 | push 06-25，稳定200K+ |
| **[chopratejas/headroom](https://github.com/chopratejas/headroom)** | **49.9K** | +0.7K | 🔥 | push 06-24，**明天破50K** |
| **[microsoft/autogen](https://github.com/microsoft/autogen)** | 59.2K | 0 | ❌ | 最后push 04-15（41天） |
| **[joaomdmoura/crewAI](https://github.com/joaomdmoura/crewAI)** | 54.3K | +0.05K | 活跃 | push 06-25 |
| **[supermemoryai/supermemory](https://github.com/supermemoryai/supermemory)** | 27.4K | +0.1K | 活跃 | push 06-24 |
| **[LangChain4j/langchain4j](https://github.com/LangChain4j/langchain4j)** | 12.4K | +0.05K | 活跃 | v1.16.3（06-18） |
| **[mnfst/manifest](https://github.com/mnfst/manifest)** | 7.1K | 0 | 缓慢 | push 06-24 |

### 重点动态

1. **headroom 49.9K** — 明天破50K。`feat(wrap): add --1m to preserve the 1M context window` 新增1M上下文窗口保留功能；`fix(proxy): keep large compression results on the critical path` 大压缩结果性能优化。
2. **hermes-agent 202K**，增速放缓至+0.5K/天，进入平台期。
3. **autogen 连续41天零更新**。

---

## 📊 趋势分析

1. **headroom 将破50K**，新增1M上下文保留功能说明在LLM长上下文场景中建立壁垒。
2. **hermes-agent 200K+稳定**，增速放缓。
3. **ECC 221K**，稳居第一。
4. **LangChain4j 停滞7天**无新版本。

---

## 📋 Nezha 状态

v0.5.2 稳定，MySQL持久化。

---

## 📜 历史扫描

<details>
<summary>2026-06-24</summary>

## 🔥 重点仓库状态（2026-06-24）

| 项目 | ⭐ Stars | 2日变化 | 状态 | 说明 |
|------|---------|--------|------|------|
| **[affaan-m/ECC](https://github.com/affaan-m/ECC)** | 220.9K | +1.6K | 活跃 | push 06-22 |
| **[NousResearch/hermes-agent](https://github.com/NousResearch/hermes-agent)** | **201.5K** | **+2.5K** | 🎉200K | push 06-24，**突破200K里程碑** |
| **[chopratejas/headroom](https://github.com/chopratejas/headroom)** | **49.2K** | **+4.8K** | 🔥爆发 | push 06-24，即将破50K |
| **[microsoft/autogen](https://github.com/microsoft/autogen)** | 59.2K | +0.1K | ❌ | 最后push 04-15（40天） |
| **[joaomdmoura/crewAI](https://github.com/joaomdmoura/crewAI)** | 54.3K | +0.2K | 活跃 | push 06-24 |
| **[supermemoryai/supermemory](https://github.com/supermemoryai/supermemory)** | 27.4K | +0.1K | 活跃 | push 06-24 |
| **[LangChain4j/langchain4j](https://github.com/LangChain4j/langchain4j)** | 12.4K | +0.1K | 活跃 | v1.16.3（06-18） |
| **[mnfst/manifest](https://github.com/mnfst/manifest)** | 7.1K | +0.05K | 缓慢 | push 06-24 |

### 重点动态

1. **🎉 hermes-agent 突破200K星！** — 成为Agent赛道第三大仓库（仅次于ECC 221K、crewAI 54K），2日增+2.5K。新版relay终端4401、浏览器验证修复。
2. **headroom 49.2K** — 2日+4.8K，v0.26.0爆发后仍保持强劲增长。关键修复：Anthropic读超时、tree-sitter UTF-8字节偏移、类成员容器压缩。**明天破50K。**
3. **autogen 连续40天零更新**。

---

## 📊 趋势分析

1. **headroom 49.2K→即将50K**：v0.26.0 Copilot BYOK引发连锁爆发，从vp0.23.0（06-04）到50K仅20天。
2. **hermes-agent 200K**：WhatsApp + Docker + Relay终端扩展策略生效。
3. **ECC 221K**，稳居Agent赛道第一。
4. **LangChain4j 停滞6天**无新版本。

---

## 📋 Nezha 状态

v0.5.2 稳定，MySQL持久化。

---

## 📜 历史扫描

<details>
<summary>2026-06-22</summary>

## 🔥 重点仓库状态（2026-06-22）

| 项目 | ⭐ Stars | 日变化 | 状态 | 说明 |
|------|---------|--------|------|------|
| **[affaan-m/ECC](https://github.com/affaan-m/ECC)** | 219.3K | +0.4K | 活跃 | push 06-21 |
| **[NousResearch/hermes-agent](https://github.com/NousResearch/hermes-agent)** | **199K** | +0.7K | 🔜200K | push 06-22，即将破200K |
| **[chopratejas/headroom](https://github.com/chopratejas/headroom)** | **44.4K** | **+2.5K** | 🔥爆发 | push 06-21，Windows UTF-8修复 |
| **[microsoft/autogen](https://github.com/microsoft/autogen)** | 59.1K | 0 | ❌ | 最后push 04-15（38天） |
| **[joaomdmoura/crewAI](https://github.com/joaomdmoura/crewAI)** | 54.1K | +0.1K | 活跃 | push 06-20 |
| **[supermemoryai/supermemory](https://github.com/supermemoryai/supermemory)** | 27.3K | +0.05K | 活跃 | push 06-21 |
| **[LangChain4j/langchain4j](https://github.com/LangChain4j/langchain4j)** | 12.4K | +0.05K | 活跃 | v1.16.3（06-18） |
| **[mnfst/manifest](https://github.com/mnfst/manifest)** | 7.1K | +0.05K | 缓慢 | push 06-19 |

### 重点动态

1. **headroom 44.4K** — 日增+2.5K仍然强劲，v0.26.0后进入稳定修复：`Fix headroom learn crashing/no-op on Windows from missing UTF-8`（Windows中文用户福音！），`fix(e2e): align Codex wrap e2e`。
2. **hermes-agent 199K** — **明天就破200K**，成为该赛道第三大仓库。
3. **autogen 连续38天零更新**。

---

## 📊 趋势分析

1. **headroom 44.4K**，v0.26.0爆发后仍有+2.5K/天，Windows UTF-8修复说明中文用户群被激活。
2. **hermes-agent 将破200K**，增速稳定在+0.7K/天。
3. **ECC 219K+**，稳居Agent赛道第一。
4. **LangChain4j 无新版本**，v1.16.3后4天无新发。

---

## 📋 Nezha 状态

v0.5.2 稳定，MySQL持久化。

---

## 📜 历史扫描

<details>
<summary>2026-06-21</summary>

## 🔥 重点仓库状态（2026-06-21）

| 项目 | ⭐ Stars | 3日变化 | 状态 | 说明 |
|------|---------|--------|------|------|
| **[affaan-m/ECC](https://github.com/affaan-m/ECC)** | 218.9K | +1.6K | 活跃 | push 06-21 |
| **[NousResearch/hermes-agent](https://github.com/NousResearch/hermes-agent)** | 198.3K | +2.1K | 活跃 | push 06-21，WhatsApp桥接修复 |
| **[chopratejas/headroom](https://github.com/chopratejas/headroom)** | **41.9K** | **+10.3K** | 🔥爆发 | push 06-20，Copilot BYOK + Claude Code集成 |
| **[microsoft/autogen](https://github.com/microsoft/autogen)** | 59.1K | +0.05K | ❌ | 最后push 04-15（37天） |
| **[joaomdmoura/crewAI](https://github.com/joaomdmoura/crewAI)** | 54K | +0.2K | 活跃 | push 06-20 |
| **[supermemoryai/supermemory](https://github.com/supermemoryai/supermemory)** | 27.2K | +0.1K | 活跃 | push 06-20 |
| **[LangChain4j/langchain4j](https://github.com/LangChain4j/langchain4j)** | 12.4K | +0.1K | 活跃 | **v1.16.3 发布**（06-18） |
| **[mnfst/manifest](https://github.com/mnfst/manifest)** | 7.1K | +0.05K | 缓慢 | push 06-19 |

### 🔥 重大动态

1. **🚀 headroom 3天暴涨+10.3K至41.9K！** — 这是该项目的爆发性增长，可能被知名媒体/播客推荐。v0.26.0新增Copilot BYOK（自带密钥），v0.23.0起已支持GitHub Copilot订阅模式。最新commit：`docs(vertex): Claude Code + Vertex via Headroom guide`，说明正在与谷歌云深度集成。
2. **LangChain4j v1.16.3** — **重要安全修复**：修复SQL注入漏洞（影响mariadb和pgvector模块），强烈建议升级。
3. **hermes-agent 逼近200K** — WhatsApp桥接修复，Docker部署优化持续。
4. **ECC 218.9K** — 稳居Agent性能优化赛道第一。

---

## 📊 趋势分析

1. **headroom 41.9K 是现象级爆发**：3天+10.3K远超正常增速（平时+1.3K/天），Copilot BYOK + Vertex AI 集成说明其商业模式在成熟。
2. **LangChain4j v1.16.3 安全修复**：SQL注入漏洞是严重问题，Nezha的MySQL查询也需检查是否有类似风险。
3. **hermes-agent 将破200K**，WhatsApp集成说明多平台扩展策略。
4. **autogen 37天零更新**，已无关注价值。

---

## 📋 Nezha 状态

v0.5.2 稳定，MySQL持久化。

**安全提示**：LangChain4j v1.16.3修复了SQL注入，Nezha中使用了动态SQL查询（如AgentService、MemoryService等），建议后续检查是否存在类似问题。

---

## 📜 历史扫描

<details>
<summary>2026-06-18</summary>

## 🔥 重点仓库状态（2026-06-18）

| 项目 | ⭐ Stars | 日变化 | 状态 | 说明 |
|------|---------|--------|------|------|
| **[affaan-m/ECC](https://github.com/affaan-m/ECC)** | 217.3K | +0.6K | 活跃 | push 06-17 |
| **[NousResearch/hermes-agent](https://github.com/NousResearch/hermes-agent)** | 196.2K | +0.8K | 活跃 | push 06-17，xai搜索修复 |
| **[microsoft/autogen](https://github.com/microsoft/autogen)** | 59K | 0 | ❌ 停滞 | 最后push 04-15（35天） |
| **[joaomdmoura/crewAI](https://github.com/joaomdmoura/crewAI)** | 53.8K | +0.1K | 活跃 | push 06-18 |
| **[chopratejas/headroom](https://github.com/chopratejas/headroom)** | **31.6K** | **+1.5K** | 🚀 | push 06-17，Dashboard UI修复 |
| **[supermemoryai/supermemory](https://github.com/supermemoryai/supermemory)** | 27.2K | +0.05K | 活跃 | push 06-18 |
| **[LangChain4j/langchain4j](https://github.com/LangChain4j/langchain4j)** | 12.4K | +0.05K | 活跃 | v1.16.2（06-10），已稳定8天 |
| **[mnfst/manifest](https://github.com/mnfst/manifest)** | 7K | 0 | 缓慢 | push 06-17 |

### 重点动态

1. **headroom 31.6K** — 日增+1.5K持续强劲，v0.26.0后进入稳定修复期：代理端点修复、Dashboard浅色模式与对齐修复。
2. **hermes-agent Docker修复** — `fix(docker): supervised gateway uses --replace to take over stale hold`，Docker部署稳定性改进。
3. **hermes-agent xai搜索修复** — `fix(xai): scope native web_search to swap-only`，xAI模型搜索范围限制。
4. **autogen 35天零更新**。

---

## 📊 趋势分析

1. **headroom 31.6K**，后v0.26.0进入修复期（Dashboard UI + proxy），增长仍然+1.5K/天。
2. **hermes-agent 持续优化Docker部署**和模型兼容性（xai搜索修复），196K+。
3. **LangChain4j v1.16.2 已稳定8天**，可能在准备v1.17大版本。
4. **ECC 217K+**，稳居Agent性能优化赛道第一。

---

## 📋 Nezha 状态

v0.5.2 稳定，MySQL持久化。

---

## 📜 历史扫描

<details>
<summary>2026-06-17</summary>

## 🔥 重点仓库状态（2026-06-17）

| 项目 | ⭐ Stars | 日变化 | 状态 | 说明 |
|------|---------|--------|------|------|
| **[affaan-m/ECC](https://github.com/affaan-m/ECC)** | 216.7K | +0.5K | 活跃 | push 06-16 |
| **[NousResearch/hermes-agent](https://github.com/NousResearch/hermes-agent)** | 195.4K | +0.9K | 活跃 | push 06-17，消息时间戳功能 |
| **[microsoft/autogen](https://github.com/microsoft/autogen)** | 59K | 0 | ❌ 停滞 | 最后push 04-15（34天） |
| **[joaomdmoura/crewAI](https://github.com/joaomdmoura/crewAI)** | 53.7K | +0.1K | 活跃 | push 06-16 |
| **[chopratejas/headroom](https://github.com/chopratejas/headroom)** | **30.1K** | **+1.3K** | 🎉 | push 06-16，**v0.26.0 + 30K里程碑** |
| **[supermemoryai/supermemory](https://github.com/supermemoryai/supermemory)** | 27.1K | +0.05K | 活跃 | push 06-17 |
| **[LangChain4j/langchain4j](https://github.com/LangChain4j/langchain4j)** | 12.3K | +0.05K | 活跃 | v1.16.2（06-10） |
| **[mnfst/manifest](https://github.com/mnfst/manifest)** | 7K | +0.05K | 缓慢 | push 06-16 |

### 重点动态

1. **🎉 headroom 突破30K星！** — v0.26.0发布。版本节奏极快：v0.24.0（06-09）→ v0.25.0（06-12）→ v0.26.0（06-16），维持2-3天一版本。
2. **hermes-agent 消息时间戳** — `feat(gateway): gate message timestamps behind opt-in`，新增可选消息时间戳。
3. **autogen 连续34天零更新**。

---

## 📊 趋势分析

1. **headroom 30K里程碑+超快迭代**：9天发3版（v0.24→v0.26），并发安全修复基本完成，进入快速功能和修复循环。
2. **hermes-agent 195K+**，工作日增速+0.9K/天，功能侧重UX（时间戳、安全修复）。
3. **LangChain4j 停滞6天**无新版本。

---

## 📋 Nezha 状态

v0.5.2 稳定，MySQL持久化。

---

## 📜 历史扫描

<details>
<summary>2026-06-16</summary>

## 🔥 重点仓库状态（2026-06-16）

| 项目 | ⭐ Stars | 日变化 | 状态 | 说明 |
|------|---------|--------|------|------|
| **[affaan-m/ECC](https://github.com/affaan-m/ECC)** | 216.2K | +0.7K | 活跃 | push 06-15 |
| **[NousResearch/hermes-agent](https://github.com/NousResearch/hermes-agent)** | 194.5K | +1K | 活跃 | push 06-16，技能递归删除安全修复 |
| **[microsoft/autogen](https://github.com/microsoft/autogen)** | 59K | +0.05K | ❌ 停滞 | 最后push 04-15（连续33天零更新） |
| **[joaomdmoura/crewAI](https://github.com/joaomdmoura/crewAI)** | 53.6K | +0.1K | 活跃 | push 06-15 |
| **[supermemoryai/supermemory](https://github.com/supermemoryai/supermemory)** | 27.1K | +0.1K | 活跃 | push 06-15 |
| **[chopratejas/headroom](https://github.com/chopratejas/headroom)** | 28.8K | **+1.2K** | 🚀活跃 | push 06-15，tree-sitter线程安全修复 |
| **[LangChain4j/langchain4j](https://github.com/LangChain4j/langchain4j)** | 12.3K | +0.05K | 活跃 | v1.16.2（06-10） |
| **[mnfst/manifest](https://github.com/mnfst/manifest)** | 7K | +0.05K | 缓慢 | push 06-15 |

### 重点动态

1. **headroom 28.8K星** — push 06-15，`fix(compression): use thread-local tree-sitter parsers in code handler` 修复了代码压缩handler中的线程安全问题（与06-10的fix(transforms)对应），说明该项目在系统性加固并发安全。
2. **hermes-agent 技能安全修复** — `fix(skills): guard recursive skill delete against tree-escape`，防止递归删除技能时路径逃逸。
3. **ECC v2.0 稳定** — 06-15有context-size/compact触发修复。
4. **autogen 连续33天零更新**。

---

## 📊 趋势分析

1. **headroom 稳在28K+**，系统性修复并发安全（transforms + code handler两处），进入生产可用阶段。
2. **hermes-agent 周一工作日恢复+1K/天**，技能安全修复说明该项目在关注用户生产环境安全问题。
3. **ECC 216K+**，稳居Agent性能优化赛道第一。
4. **LangChain4j v1.16.2 已稳定6天**，无新版本，可能正在准备v1.17。

---

## 📋 Nezha 状态

v0.5.2 稳定，MySQL持久化。最近新增 GroupChatPipeline（commit 598619f）。

---

## 📜 历史扫描

<details>
<summary>2026-06-15</summary>

## 🔥 重点仓库状态（2026-06-15）

| 项目 | ⭐ Stars | 日变化 | 状态 | 说明 |
|------|---------|--------|------|------|
| **[affaan-m/ECC](https://github.com/affaan-m/ECC)** | 215.5K | +0.6K | 活跃 | push 06-11 |
| **[NousResearch/hermes-agent](https://github.com/NousResearch/hermes-agent)** | 193.5K | +0.7K | 活跃 | push 06-14，revert无键并行搜索回退 |
| **[microsoft/autogen](https://github.com/microsoft/autogen)** | 58.9K | 0 | ❌ 停滞 | 最后push 04-15（连续32天零更新） |
| **[joaomdmoura/crewAI](https://github.com/joaomdmoura/crewAI)** | 53.6K | +0.1K | 活跃 | push 06-13 |
| **[supermemoryai/supermemory](https://github.com/supermemoryai/supermemory)** | 27K | +0.1K | 活跃 | push 06-13 |
| **[chopratejas/headroom](https://github.com/chopratejas/headroom)** | 27.6K | **+1.4K** | 🚀活跃 | push 06-14，Windows hang修复 |
| **[LangChain4j/langchain4j](https://github.com/LangChain4j/langchain4j)** | 12.3K | +0.05K | 活跃 | v1.16.2（06-10） |
| **[mnfst/manifest](https://github.com/mnfst/manifest)** | 6.9K | 0 | 缓慢 | push 06-12 |

### 重点动态

1. **headroom 27.6K星** — 日增+1.4K，`Fix/magika new session hangs on windows` 修复Windows下的会话挂起问题，说明该项目开始关注多平台稳定性。
2. **hermes-agent revert无键fallback** — `revert(web): remove keyless Parallel search fallback`，说明并行搜索功能有回归，回退了。
3. **ECC 稳定push** — 06-11有CI漂移修复和context-size/compact触发修复。
4. **autogen 连续32天零更新**。

---

## 📊 趋势分析

1. **headroom 突破27K**，Windows兼容性修复说明用户群在扩大。
2. **hermes-agent 增速放缓**至+0.7K/天，进入平台期。
3. **ECC 215K+**，稳居Agent性能优化榜首。
4. **LangChain4j 无新版本**，v1.16.2已稳定5天。

---

## 📋 Nezha 状态

v0.5.2 稳定，MySQL持久化。最近新增 GroupChatPipeline + CharsetMigration（commit 598619f）。

---

## 📜 历史扫描

<details>
<summary>2026-06-14</summary>

## 🔥 重点仓库状态（2026-06-14）

| 项目 | ⭐ Stars | 日变化 | 状态 | 说明 |
|------|---------|--------|------|------|
| **[affaan-m/ECC](https://github.com/affaan-m/ECC)** | 214.9K | +0.6K | 活跃 | push 06-11 |
| **[NousResearch/hermes-agent](https://github.com/NousResearch/hermes-agent)** | 192.8K | +0.8K | 活跃 | push 06-13，桌面端审批UI修复 |
| **[microsoft/autogen](https://github.com/microsoft/autogen)** | 58.9K | 0 | ❌ 停滞 | 最后push 04-15（连续30天零更新） |
| **[joaomdmoura/crewAI](https://github.com/joaomdmoura/crewAI)** | 53.5K | +0.1K | 活跃 | push 06-13 |
| **[supermemoryai/supermemory](https://github.com/supermemoryai/supermemory)] | 26.9K | +0.1K | 活跃 | push 06-13 |
| **[chopratejas/headroom](https://github.com/chopratejas/headroom)** | 26.2K | **+1.5K** | 🚀活跃 | push 06-13，MCP输入Schema标准化 |
| **[LangChain4j/langchain4j](https://github.com/LangChain4j/langchain4j)** | 12.3K | +0.01K | 活跃 | v1.16.2（06-10） |
| **[mnfst/manifest](https://github.com/mnfst/manifest)** | 6.9K | 0 | 缓慢 | push 06-12 |

### 重点动态

1. **headroom 26.2K星** — 日增+1.5K持续强劲，`Normalize headroom_stats MCP input schema` MCP接口标准化，`session probes` 评估文档完善。
2. **hermes-agent 桌面端修复** — `surface off-screen approvals via jump-to-bottom control`。
3. **autogen 连续30天零更新**（满月），正式标记为死项目。
4. **LangChain4j v1.16.2 已稳定4天**，无新版本。

---

## 📊 趋势分析

1. **headroom 26K+，增长持续强劲**（+1.5K/天），MCP Schema标准化说明生态在成熟。
2. **hermes-agent +0.8K/天**，周末略降，桌面端UX持续优化。
3. **autogen 30天整月零更新**，可正式从所有关注列表移除。
4. **周末整体活跃度降低**，无重大突破。

---

## 📋 Nezha 状态

v0.5.2 稳定，MySQL持久化。最近新增 GroupChatPipeline + CharsetMigration（commit 598619f）。

---

## 📜 历史扫描

<details>
<summary>2026-06-13</summary>

## 🔥 重点仓库状态（2026-06-13）

| 项目 | ⭐ Stars | 日变化 | 状态 | 说明 |
|------|---------|--------|------|------|
| **[affaan-m/ECC](https://github.com/affaan-m/ECC)** | 214.3K | +0.8K | 活跃 | push 06-11 |
| **[NousResearch/hermes-agent](https://github.com/NousResearch/hermes-agent)** | 192K | +1K | 活跃 | push 06-12，新增Kimi K2.7支持 |
| **[microsoft/autogen](https://github.com/microsoft/autogen)** | 58.9K | 0 | ❌ 停滞 | 最后push 04-15（连续29天零更新） |
| **[joaomdmoura/crewAI](https://github.com/joaomdmoura/crewAI)** | 53.4K | +0.1K | 活跃 | push 06-12 |
| **[supermemoryai/supermemory](https://github.com/supermemoryai/supermemory)] | 26.9K | +0.1K | 活跃 | push 06-13 |
| **[chopratejas/headroom](https://github.com/chopratejas/headroom)** | 24.7K | **+1.5K** | 🚀活跃 | push 06-12，Bedrock + 冗余工具调用检测 |
| **[LangChain4j/langchain4j](https://github.com/LangChain4j/langchain4j)** | 12.3K | +0.1K | 活跃 | v1.16.2（06-10） |
| **[mnfst/manifest](https://github.com/mnfst/manifest)** | 6.9K | 0 | 缓慢 | push 06-12 |

### 重点动态

1. **headroom 24.7K星** — 日增+1.5K，`feat(parser): detect re-issued identical tool calls as reread waste` 新增冗余工具调用检测；`fix(proxy): add native Bedrock converse-stream route` 增加AWS Bedrock支持。
2. **hermes-agent 新增Kimi K2.7支持** — 扩展模型兼容性。
3. **autogen 连续29天零更新**。

---

## 📊 趋势分析

1. **headroom 加速突破24K**（+1.5K/天），Bedrock支持和冗余检测说明产品在快速成熟。
2. **hermes-agent 持续+1K/天**，Kimi K2.7支持说明其模型适配范围在扩大。
3. **LangChain4j 无新版本**，v1.16.2已稳定3天。
4. **整体格局不变**，周末流量略有下降。

---

## 📋 Nezha 状态

v0.5.2 稳定运行，MySQL持久化。BrowserTool + SystemTool 已集成（commit d157b8c）。

| 功能 | 状态 |
|------|------|
| 长期记忆 | ✅ MySQL |
| 定时任务 | ✅ Cron |
| Token压缩 | ✅ |
| 模型路由 | ✅ |
| 编排模式 | ✅ 4种 |
| 文件上传 | ✅ |
| Persona模板 | ✅ |
| Artifact日志 | ✅ |
| 浏览器工具 | ✅ |
| 系统工具 | ✅ |

---

## 📜 历史扫描

<details>
<summary>2026-06-12</summary>

## 🔥 重点仓库状态（2026-06-12）

| 项目 | ⭐ Stars | 日变化 | 状态 | 说明 |
|------|---------|--------|------|------|
| **[affaan-m/ECC](https://github.com/affaan-m/ECC)** | 213.5K | +0.8K | 活跃 | push 06-11 |
| **[NousResearch/hermes-agent](https://github.com/NousResearch/hermes-agent)** | 191K | +1K | 活跃 | push 06-12，**MCP能力门控修复** |
| **[microsoft/autogen](https://github.com/microsoft/autogen)** | 58.9K | 0 | ❌ 停滞 | 最后push 04-15（连续28天零更新） |
| **[joaomdmoura/crewAI](https://github.com/joaomdmoura/crewAI)** | 53.3K | +0.1K | 活跃 | push 06-11 |
| **[supermemoryai/supermemory](https://github.com/supermemoryai/supermemory)** | 26.8K | +0.3K | 活跃 | push 06-11 |
| **[chopratejas/headroom](https://github.com/chopratejas/headroom)** | 23.2K | **+1.4K** | 🚀活跃 | push 06-12，代理客户端追踪修复 |
| **[LangChain4j/langchain4j](https://github.com/LangChain4j/langchain4j)** | 12.3K | +0.05K | 活跃 | v1.16.2（06-10） |
| **[mnfst/manifest](https://github.com/mnfst/manifest)** | 6.9K | 0 | 缓慢 | push 06-11 |

### 重点动态

1. **headroom 23.2K星** — 日增+1.4K持续强劲，`fix(wrap): track shared proxy clients with markers` 修复代理客户端追踪问题。
2. **hermes-agent MCP能力门控修复** — `fix(mcp): capability-gate tools/list so prompt-only MCP servers can co-exist` — MCP服务器兼容性提升。
3. **autogen 连续28天零更新**。

---

## 📊 趋势分析

1. **headroom 持续爆发**，23.2K（+1.4K/天），进入稳定高速增长期。
2. **hermes-agent 稳定在+1K/天**，MCP兼容性问题修复说明生态扩展在推进。
3. **ECC 213K+**稳步增长，Agent性能优化赛道持续领跑。
4. **LangChain4j v1.16.2 稳定**，无新版本发布。


---

## 📋 Nezha v0.5.2 状态

新增 BrowserToolService + SystemToolService（commit d157b8c），MySQL持久化稳定运行。

| 功能 | 状态 |
|------|------|
| 长期记忆 | ✅ MySQL持久化 |
| 定时任务 | ✅ Cron调度 |
| Token压缩 | ✅ CompressService |
| 模型路由 | ✅ ModelRouter |
| 编排模式 | ✅ 4种Pipeline |
| 文件上传 | ✅ FileService |
| Persona模板 | ✅ 6内置+自定义 |
| Artifact日志 | ✅ 自动工作日志 |
| 浏览器工具 | ✅ BrowserToolService |
| 系统工具 | ✅ SystemToolService |

---

## 📜 历史扫描

<details>
<summary>2026-06-11</summary>

## 🔥 重点仓库状态（2026-06-11）

| 项目 | ⭐ Stars | 日变化 | 状态 | 说明 |
|------|---------|--------|------|------|
| **[affaan-m/ECC](https://github.com/affaan-m/ECC)** | 212.7K | +0.8K | 活跃 | push 06-10 |
| **[NousResearch/hermes-agent](https://github.com/NousResearch/hermes-agent)** | 190K | +1.2K | 活跃 | push 06-11，.env解析修复 |
| **[microsoft/autogen](https://github.com/microsoft/autogen)** | 58.9K | 0 | ❌ 停滞 | 最后push 04-15（连续27天零更新） |
| **[joaomdmoura/crewAI](https://github.com/joaomdmoura/crewAI)** | 53.2K | +0.1K | 活跃 | push 06-11 |
| **[supermemoryai/supermemory](https://github.com/supermemoryai/supermemory)** | 26.5K | +0.2K | 活跃 | push 06-11 |
| **[chopratejas/headroom](https://github.com/chopratejas/headroom)** | 21.8K | +1.2K | 🚀活跃 | push 06-10，**线程安全修复** |
| **[LangChain4j/langchain4j](https://github.com/LangChain4j/langchain4j)** | 12.3K | +0.05K | 活跃 | **v1.16.2发布**（06-10） |
| **[mnfst/manifest](https://github.com/mnfst/manifest)** | 6.9K | 0 | 缓慢 | push 06-10 |

### 重点动态

1. **headroom 21.8K星** — 修复线程安全问题（thread-local tree-sitter parsers），说明开始支持高并发场景。
2. **LangChain4j v1.16.2发布** — v1.16.1后仅4天，快速补丁节奏。
3. **hermes-agent .env解析修复** — HERMES_HOME环境变量解析更健壮。
4. **autogen 连续27天零更新**，正式停止维护。

---

## 📊 趋势分析

1. **headroom 增长放缓至+1.2K/天** — 高速爆发期已过，进入稳定增长。并发安全修复是重要里程碑。
2. **LangChain4j 持续快速迭代**（13天3个版本）— Java生态Agent框架最活跃。
3. **autogen 27天零更新**可从观察名单移除。
4. **本周无新Agent框架进入Trending**。

---

## 📋 Nezha v0.5.1 状态

MySQL 8.4 持久化已完成，功能稳定。

| 功能 | 状态 |
|------|------|
| 长期记忆 | ✅ MySQL持久化 |
| 定时任务 | ✅ Cron调度 |
| Token压缩 | ✅ CompressService |
| 模型路由 | ✅ ModelRouter |
| 编排模式 | ✅ 4种Pipeline |
| 文件上传 | ✅ FileService |
| Persona模板 | ✅ 6内置+自定义 |
| Artifact日志 | ✅ 自动工作日志 |
| MySQL持久化 | ✅ |

### 下次关注
- LangChain4j v1.16.x 新增Agent模式
- headroom 并发安全修复参考

---

## 📜 历史扫描

<details>
<summary>2026-06-10</summary>

## 🔥 重点仓库状态（2026-06-10）

| 项目 | ⭐ Stars | 日变化 | 状态 | 说明 |
|------|---------|--------|------|------|
| **[affaan-m/ECC](https://github.com/affaan-m/ECC)** | 211.9K | +0.9K | 活跃 | push 06-10 |
| **[NousResearch/hermes-agent](https://github.com/NousResearch/hermes-agent)** | 188.8K | +1.8K | 活跃 | push 06-10，持续爆发 |
| **[microsoft/autogen](https://github.com/microsoft/autogen)** | 58.8K | -0.2K | ❌ 停滞 | 最后push 04-15（连续25天零更新） |
| **[joaomdmoura/crewAI](https://github.com/joaomdmoura/crewAI)** | 53.2K | +0.2K | 活跃 | push 06-09 |
| **[supermemoryai/supermemory](https://github.com/supermemoryai/supermemory)** | 26.3K | +0.1K | 活跃 | push 06-09 |
| **[chopratejas/headroom](https://github.com/chopratejas/headroom)** | 20.6K | **+1.7K** | 🚀爆发 | push 06-10，压缩算法修复 |
| **[LangChain4j/langchain4j](https://github.com/LangChain4j/langchain4j)** | 12.3K | +0.1K | 活跃 | **v1.16.1发布**（06-06） |
| **[mnfst/manifest](https://github.com/mnfst/manifest)** | 6.9K | 0 | 缓慢 | push 06-09 |

### 重点动态

1. **headroom 20K星突破** 🎉 — 日增+1.7K，关键commit：`fix: schema compaction must not drop property names that match DROP_KEYS`，压缩可靠性提升。新增 `savings history rollups per provider` 统计功能。
2. **LangChain4j v1.16.1发布** — 06-06正式发布，距v1.15.1仅9天，迭代加速。值得关注是否有新的Agent模式。
3. **hermes-agent 持续爆发** — 日增+1.8K，修复桌面端和终端bug。
4. **autogen 连续25天零更新**，实质已死。

---

## 📊 趋势分析

1. **headroom 突破20K星**，压缩可靠性修复（DROP_KEYS bug）说明该库在快速迭代中。Nezha的CompressService目前仅做首尾保留+中间折叠，可参考headroom的schema compaction思路升级。
2. **LangChain4j v1.16.1快速迭代**（v1.15.1→1.16.0→1.16.1，9天2个版本），Java生态Agent框架持续活跃。
3. **autogen 可正式标记为停止维护**，25天零更新。
4. **本周无新Agent框架进入Trending**，赛道格局稳定。

---

## 📋 Nezha v0.5.1 状态

已迁移到 MySQL 8.4 持久化（commit 3bb5750），新增文件上传功能。

| 功能 | 状态 |
|------|------|
| 长期记忆 | ✅ MySQL持久化 + MemoryService |
| 定时任务 | ✅ Cron调度 + TaskService |
| Token压缩 | ✅ CompressService（首尾保留） |
| 子Agent并行 | ✅ BroadcastPipeline |
| 模型路由 | ✅ ModelRouter |
| 编排模式 | ✅ Sequential/Broadcast/Loop/IfElse |
| 文件上传 | ✅ FileService + 预览/下载 |
| Persona模板 | ✅ 6个内置 + 自定义 |
| Artifact日志 | ✅ 自动工作日志 |
| MySQL持久化 | ✅ 迁移完成 |

### 下次关注
- headroom schema compaction 算法细节，是否可移植到Java
- LangChain4j v1.16.x 新增的Agent模式

---

## 📜 历史扫描

<details>
<summary>2026-06-09</summary>

## 🔥 重点仓库状态（2026-06-09）

| 项目 | ⭐ Stars | 日变化 | 状态 | 说明 |
|------|---------|--------|------|------|
| **[affaan-m/ECC](https://github.com/affaan-m/ECC)** | 211K | +14 | 活跃 | push 06-07 |
| **[NousResearch/hermes-agent](https://github.com/NousResearch/hermes-agent)** | 187K | +452 | 活跃 | push 06-09 |
| **[microsoft/autogen](https://github.com/microsoft/autogen)** | 59K | 0 | ⚠️ 停滞 | 最后push 04-15（第3天无更新） |
| **[MemPalace/mempalace](https://github.com/MemPalace/mempalace)** | 55K | +342 | 活跃 | 记忆系统 |
| **[joaomdmoura/crewAI](https://github.com/joaomdmoura/crewAI)** | 53K | +88 | 活跃 | push 06-09 |
| **[supermemoryai/supermemory](https://github.com/supermemoryai/supermemory)** | 26.2K | +190 | 活跃 | |
| **[chopratejas/headroom](https://github.com/chopratejas/headroom)** | 18.9K | **+1,952** | 🚀爆发 | 日增近2K，2天+3K |
| **[LangChain4j/langchain4j](https://github.com/LangChain4j/langchain4j)** | 12.2K | +13 | 活跃 | push 06-08 |
| **[mnfst/manifest](https://github.com/mnfst/manifest)** | 6.9K | +23 | 活跃 | push 06-08 |

### 本周新秀

| 项目 | ⭐ | 说明 |
|------|---|------|
| NoopApp/noop | 531 | 离线WHOOP伴侣（非Agent） |
| JimLiu/baoyu-design | 520 | Claude Design本地Agent Skill（非框架） |
| tastyeffectco/sandboxd | 514 | 自托管开发沙箱，可参考Agent隔离 |

---

## 📊 趋势分析

1. **headroom 单日+1,952星** 🚀，加速增长。从6月7日16.9K→6月9日18.9K（3天+4.4K），Token压缩需求持续爆发。
2. **hermes-agent 回暖**：日增+452，恢复增长势头。
3. **autogen 连续3天零更新**（04-15后），已实质停滞。
4. **本周无Agent框架新星**进入Trending，赛道格局稳定。

---

## 📋 Nezha v0.4.0 状态

全部核心功能已完成，无紧急集成需求。

---

## 📜 历史扫描

<details>
<summary>2026-06-08</summary>

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

</details>

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
