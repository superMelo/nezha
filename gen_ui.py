#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""Generate Nezha index.html with embedded i18n"""

html = '''<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Nezha</title>
<style>
:root {
  --bg: #1a1a2e;
  --sidebar-bg: #16213e;
  --border: #0f3460;
  --text: #e0e0e0;
  --text2: #999;
  --accent: #e94560;
  --accent2: #c73e54;
  --hover: #0f3460;
  --input-bg: #1a1a2e;
  --bubble-ai: #16213e;
  --header-bg: #16213e;
  --badge-bg: #e94560;
  --modal-bg: #1a1a2e;
}
[data-theme="light"] {
  --bg: #f0f2f5;
  --sidebar-bg: #ffffff;
  --border: #e0e0e0;
  --text: #333333;
  --text2: #888888;
  --accent: #e94560;
  --accent2: #c73e54;
  --hover: #e8e8e8;
  --input-bg: #ffffff;
  --bubble-ai: #ffffff;
  --header-bg: #ffffff;
  --badge-bg: #e94560;
  --modal-bg: #ffffff;
}
* { margin: 0; padding: 0; box-sizing: border-box; }
body {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
  background: var(--bg);
  color: var(--text);
  height: 100vh;
  overflow: hidden;
}
.header {
  background: var(--header-bg);
  padding: 12px 20px;
  display: flex;
  align-items: center;
  gap: 10px;
  border-bottom: 1px solid var(--border);
  height: 49px;
}
.header h1 { font-size: 18px; color: var(--accent); }
.header .badge {
  background: var(--badge-bg);
  color: white;
  padding: 2px 8px;
  border-radius: 10px;
  font-size: 11px;
}
.header .spacer { flex: 1; }
.header-btns { display: flex; gap: 6px; align-items: center; }
.header-btn {
  padding: 5px 10px;
  border: 1px solid var(--border);
  border-radius: 6px;
  background: transparent;
  color: var(--text);
  cursor: pointer;
  font-size: 12px;
  transition: 0.2s;
}
.header-btn:hover { background: var(--hover); }
.container { display: flex; height: calc(100vh - 49px); }
.sessions {
  width: 220px;
  background: var(--sidebar-bg);
  border-right: 1px solid var(--border);
  display: flex;
  flex-direction: column;
}
.sessions .top {
  padding: 10px;
  border-bottom: 1px solid var(--border);
  display: flex;
  gap: 6px;
}
.sessions .top button {
  flex: 1;
  padding: 7px;
  border: 1px solid var(--border);
  border-radius: 6px;
  background: transparent;
  color: var(--text);
  cursor: pointer;
  font-size: 12px;
}
.sessions .top button:hover { background: var(--hover); }
.session-list { flex: 1; overflow-y: auto; padding: 6px; }
.session-item {
  padding: 8px 10px;
  border-radius: 6px;
  cursor: pointer;
  margin-bottom: 2px;
  font-size: 13px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  display: flex;
  justify-content: space-between;
  align-items: center;
  transition: 0.2s;
}
.session-item:hover { background: var(--hover); }
.session-item.active { background: var(--accent); color: white; }
.session-item .del { display: none; font-size: 16px; margin-left: 4px; cursor: pointer; }
.session-item:hover .del { display: block; }
.sidebar {
  width: 220px;
  background: var(--sidebar-bg);
  border-right: 1px solid var(--border);
  overflow-y: auto;
  padding: 10px;
}
.sidebar h3 {
  color: var(--accent);
  font-size: 12px;
  margin-bottom: 6px;
  text-transform: uppercase;
  letter-spacing: 1px;
}
.agent-item {
  padding: 8px 10px;
  border-radius: 6px;
  cursor: pointer;
  margin-bottom: 2px;
  transition: 0.2s;
}
.agent-item:hover { background: var(--hover); }
.agent-item.active { background: var(--accent); color: white; }
.agent-item .name { font-weight: 600; font-size: 13px; }
.agent-item .info { font-size: 11px; opacity: 0.6; margin-top: 2px; }
.mode-section { margin-top: 12px; }
.mode-btn {
  width: 100%;
  padding: 7px;
  border: 1px solid var(--border);
  border-radius: 6px;
  background: transparent;
  color: var(--text);
  cursor: pointer;
  margin-bottom: 3px;
  text-align: left;
  font-size: 12px;
  transition: 0.2s;
}
.mode-btn:hover { background: var(--hover); }
.mode-btn.active { background: var(--accent); border-color: var(--accent); }
.main { flex: 1; display: flex; flex-direction: column; min-width: 0; }
.chat-area { flex: 1; overflow-y: auto; padding: 16px; }
.msg { margin-bottom: 12px; max-width: 80%; }
.msg.user { margin-left: auto; }
.msg.assistant { margin-right: auto; }
.msg .bubble {
  padding: 10px 14px;
  border-radius: 12px;
  font-size: 14px;
  line-height: 1.6;
  white-space: pre-wrap;
  word-wrap: break-word;
}
.msg.user .bubble {
  background: var(--accent);
  color: white;
  border-bottom-right-radius: 4px;
}
.msg.assistant .bubble {
  background: var(--bubble-ai);
  border: 1px solid var(--border);
  border-bottom-left-radius: 4px;
}
.msg .meta { font-size: 10px; opacity: 0.4; margin-top: 3px; }
.msg.user .meta { text-align: right; }
.input-area {
  padding: 12px 16px;
  border-top: 1px solid var(--border);
  background: var(--sidebar-bg);
}
.pipeline-agents { display: flex; gap: 6px; margin-bottom: 6px; flex-wrap: wrap; }
.pipeline-tag {
  padding: 3px 8px;
  background: var(--hover);
  border-radius: 10px;
  font-size: 12px;
  cursor: pointer;
  transition: 0.2s;
}
.pipeline-tag.selected { background: var(--accent); color: white; }
.input-row { display: flex; gap: 6px; }
.input-row textarea {
  flex: 1;
  background: var(--input-bg);
  border: 1px solid var(--border);
  border-radius: 10px;
  padding: 10px 14px;
  color: var(--text);
  font-size: 14px;
  resize: none;
  font-family: inherit;
  min-height: 40px;
  max-height: 120px;
}
.input-row textarea::placeholder { color: var(--text2); }
.input-row button {
  background: var(--accent);
  color: white;
  border: none;
  border-radius: 10px;
  padding: 0 18px;
  cursor: pointer;
  font-size: 13px;
  font-weight: 600;
  white-space: nowrap;
}
.input-row button:hover { background: var(--accent2); }
.input-row button:disabled { opacity: 0.5; }
.input-actions { display: flex; gap: 6px; margin-top: 6px; justify-content: flex-end; }
.input-actions button {
  padding: 4px 10px;
  border: 1px solid var(--border);
  border-radius: 6px;
  background: transparent;
  color: var(--text2);
  cursor: pointer;
  font-size: 11px;
}
.input-actions button:hover { border-color: var(--accent); color: var(--accent); }
.loading {
  display: inline-block;
  width: 14px;
  height: 14px;
  border: 2px solid var(--border);
  border-top-color: var(--accent);
  border-radius: 50%;
  animation: spin 0.6s linear infinite;
  margin-right: 6px;
  vertical-align: middle;
}
@keyframes spin { to { transform: rotate(360deg); } }
.empty {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  color: var(--text2);
  font-size: 15px;
  flex-direction: column;
  gap: 8px;
}
.empty .icon { font-size: 48px; }
.modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0,0,0,0.6);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 100;
}
.modal {
  background: var(--modal-bg);
  border: 1px solid var(--border);
  border-radius: 12px;
  padding: 24px;
  width: 460px;
  max-height: 80vh;
  overflow-y: auto;
}
.modal h2 { font-size: 16px; margin-bottom: 16px; color: var(--accent); }
.modal label {
  display: block;
  font-size: 12px;
  color: var(--text2);
  margin-bottom: 4px;
  margin-top: 12px;
}
.modal input,
.modal textarea,
.modal select {
  width: 100%;
  padding: 8px;
  background: var(--input-bg);
  border: 1px solid var(--border);
  border-radius: 6px;
  color: var(--text);
  font-size: 13px;
}
.modal textarea { min-height: 80px; resize: vertical; font-family: inherit; }
.modal-actions { display: flex; gap: 8px; margin-top: 16px; justify-content: flex-end; }
.modal-actions button {
  padding: 8px 16px;
  border-radius: 6px;
  cursor: pointer;
  font-size: 13px;
  border: 1px solid var(--border);
}
.modal-actions .primary { background: var(--accent); border-color: var(--accent); color: white; }
.modal-actions .secondary { background: transparent; color: var(--text); }
</style>
</head>
<body>
<div class="header">
  <h1>Nezha</h1>
  <span class="badge">v0.3.0</span>
  <div class="spacer"></div>
  <div class="header-btns">
    <button class="header-btn" id="btn-settings" onclick="showSettings()"></button>
    <button class="header-btn" id="btn-memory" onclick="showMemory()"></button>
    <button class="header-btn" id="btn-tasks" onclick="showTasks()"></button>
    <button class="header-btn" id="btn-theme" onclick="toggleTheme()"></button>
    <button class="header-btn" id="btn-lang" onclick="toggleLang()">EN/ZH</button>
  </div>
</div>
<div class="container">
  <div class="sessions">
    <div class="top">
      <button id="btn-newchat" onclick="newSession()"></button>
      <button id="btn-newagent" onclick="showAgentManager()"></button>
    </div>
    <div class="session-list" id="sessionList"></div>
  </div>
  <div class="sidebar">
    <h3 id="lbl-agents"></h3>
    <div id="agentList"></div>
    <div class="mode-section">
      <h3 id="lbl-pipeline"></h3>
      <button class="mode-btn" onclick="setMode('sequential')" id="btn-sequential"></button>
      <button class="mode-btn" onclick="setMode('broadcast')" id="btn-broadcast"></button>
    </div>
  </div>
  <div class="main">
    <div class="chat-area" id="chatArea">
      <div class="empty"><div class="icon">Nezha</div><div id="lbl-empty"></div></div>
    </div>
    <div class="input-area">
      <div class="pipeline-agents" id="pipelineAgents" style="display:none"></div>
      <div class="input-row">
        <textarea id="inputMsg" rows="1"></textarea>
        <button id="sendBtn" onclick="send()"></button>
      </div>
      <div class="input-actions">
        <button id="btn-export" onclick="exportSession()"></button>
        <button id="btn-compress" onclick="compressCurrentSession()"></button>
        <button id="btn-clear" onclick="clearMemory()"></button>
      </div>
    </div>
  </div>
</div>
<div id="modalContainer"></div>
<script>
// i18n data embedded
var ZH = {
  newChat: "+ 新对话", newAgent: "+ 智能体", settings: "设置", memory: "记忆",
  tasks: "任务", theme: "主题", agents: "智能体", pipeline: "编排",
  sequential: "串行 (A->B)", broadcast: "广播 (多智能体)",
  selectAgent: "选择智能体开始对话", clickNewChat: "点击 +新对话",
  typeMessage: "输入消息...", send: "发送", thinking: "思考中...",
  export: "导出", compress: "压缩", clear: "清除",
  createAgent: "创建智能体", editAgent: "编辑智能体",
  agentName: "名称", agentNamePh: "智能体名称",
  sysPrompt: "系统提示词", sysPromptPh: "系统提示词...",
  model: "模型", save: "保存", cancel: "取消", delete: "删除",
  deleteConfirm: "确认删除这个智能体？",
  deepseekKey: "DeepSeek API Key", openaiKey: "OpenAI API Key",
  claudeKey: "Claude API Key", qwenKey: "通义千问 API Key",
  searchMemory: "搜索记忆...", add: "添加",
  enterMemory: "输入记忆内容：", scheduledTasks: "定时任务",
  taskName: "任务名称", taskNamePh: "每日报告",
  agent: "智能体", message: "消息内容", cron: "Cron 表达式", cronPh: "0 9 * * *",
  selectFirst: "请先选择会话", compressConfirm: "压缩会话历史？",
  compressed: "压缩完成！", nameRequired: "请输入名称",
  nameMsgRequired: "请输入名称和消息", selectAgentFirst: "请先选择智能体",
  selectOneAgent: "请至少选择一个智能体", networkError: "网络错误：",
  error: "错误：", dark: "深色", light: "浅色", lang: "EN/ZH"
};
var EN = {
  newChat: "+ New Chat", newAgent: "+ Agent", settings: "Settings", memory: "Memory",
  tasks: "Tasks", theme: "Theme", agents: "Agents", pipeline: "Pipeline",
  sequential: "Sequential (A->B)", broadcast: "Broadcast (Multi)",
  selectAgent: "Select an Agent to start", clickNewChat: "Click +New Chat",
  typeMessage: "Type a message...", send: "Send", thinking: "Thinking...",
  export: "Export", compress: "Compress", clear: "Clear",
  createAgent: "Create Agent", editAgent: "Edit Agent",
  agentName: "Name", agentNamePh: "Agent name",
  sysPrompt: "System Prompt", sysPromptPh: "System prompt...",
  model: "Model", save: "Save", cancel: "Cancel", delete: "Delete",
  deleteConfirm: "Delete this agent?",
  deepseekKey: "DeepSeek API Key", openaiKey: "OpenAI API Key",
  claudeKey: "Claude API Key", qwenKey: "Qwen API Key",
  searchMemory: "Search memory...", add: "Add",
  enterMemory: "Enter memory fact:", scheduledTasks: "Scheduled Tasks",
  taskName: "Task Name", taskNamePh: "Daily Report",
  agent: "Agent", message: "Message", cron: "Cron", cronPh: "0 9 * * *",
  selectFirst: "Select a session first", compressConfirm: "Compress session history?",
  compressed: "Compressed!", nameRequired: "Name required",
  nameMsgRequired: "Name and message required", selectAgentFirst: "Please select an Agent",
  selectOneAgent: "Select at least one Agent", networkError: "Network error: ",
  error: "Error: ", dark: "Dark", light: "Light", lang: "EN/ZH"
};
var LANGS = { zh: ZH, en: EN };
var L = {}, currentLang = 'zh', currentAgent = null, currentMode = 'single';
var selectedPipelineAgents = {}, sessions = [], currentSessionId = null;

function t(k) { return L[k] || k; }

function toggleLang() {
  currentLang = currentLang === 'zh' ? 'en' : 'zh';
  localStorage.setItem('nezha-lang', currentLang);
  applyLang();
}

function applyLang() {
  L = LANGS[currentLang] || EN;
  document.getElementById('btn-settings').textContent = t('settings');
  document.getElementById('btn-memory').textContent = t('memory');
  document.getElementById('btn-tasks').textContent = t('tasks');
  document.getElementById('btn-theme').textContent = document.documentElement.getAttribute('data-theme') === 'light' ? t('light') : t('dark');
  document.getElementById('btn-lang').textContent = t('lang');
  document.getElementById('btn-newchat').textContent = t('newChat');
  document.getElementById('btn-newagent').textContent = t('newAgent');
  document.getElementById('lbl-agents').textContent = t('agents');
  document.getElementById('lbl-pipeline').textContent = t('pipeline');
  document.getElementById('btn-sequential').textContent = t('sequential');
  document.getElementById('btn-broadcast').textContent = t('broadcast');
  document.getElementById('lbl-empty').textContent = t('selectAgent');
  document.getElementById('inputMsg').placeholder = t('typeMessage');
  document.getElementById('sendBtn').textContent = t('send');
  document.getElementById('btn-export').textContent = t('export');
  document.getElementById('btn-compress').textContent = t('compress');
  document.getElementById('btn-clear').textContent = t('clear');
  document.documentElement.lang = currentLang;
}

function toggleTheme() {
  var d = document.documentElement.getAttribute('data-theme') !== 'light';
  document.documentElement.setAttribute('data-theme', d ? 'light' : '');
  localStorage.setItem('nezha-theme', d ? 'light' : 'dark');
  document.getElementById('btn-theme').textContent = d ? t('light') : t('dark');
}

document.getElementById('inputMsg').addEventListener('keydown', function(e) {
  if (e.key === 'Enter' && !e.shiftKey) { e.preventDefault(); send(); }
});

function esc(s) {
  return String(s).replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;');
}

function loadSessions() {
  fetch('/api/sessions').then(r => r.json()).then(list => {
    sessions = list;
    var el = document.getElementById('sessionList');
    el.innerHTML = '';
    list.forEach(s => {
      var div = document.createElement('div');
      div.className = 'session-item' + (currentSessionId === s.ID ? ' active' : '');
      div.innerHTML = '<span>' + esc(s.TITLE || t('newChat')) + '</span><span class="del" onclick="event.stopPropagation();deleteSession(\'' + s.ID + '\')">x</span>';
      div.onclick = () => selectSession(s.ID);
      el.appendChild(div);
    });
  });
}

function newSession() {
  var agent = currentAgent || 'Assistant';
  fetch('/api/sessions', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ agent: agent, pipelineMode: currentMode })
  }).then(r => r.json()).then(d => {
    currentSessionId = d.sessionId;
    document.getElementById('chatArea').innerHTML = '<div class="empty"><div class="icon">Nezha</div><div>' + t('selectAgent') + '</div></div>';
    loadSessions();
  });
}

function selectSession(id) {
  currentSessionId = id;
  loadSessions();
  fetch('/api/sessions/' + id + '/messages').then(r => r.json()).then(msgs => {
    var area = document.getElementById('chatArea');
    area.innerHTML = '';
    msgs.forEach(m => {
      var div = document.createElement('div');
      div.className = 'msg ' + m.ROLE;
      var tc = document.createElement('div');
      tc.textContent = m.CONTENT;
      tc.className = 'bubble';
      div.appendChild(tc);
      if (m.ELAPSED_MS) {
        var meta = document.createElement('div');
        meta.className = 'meta';
        meta.textContent = m.ELAPSED_MS + 'ms';
        div.appendChild(meta);
      }
      area.appendChild(div);
    });
    area.scrollTop = area.scrollHeight;
  });
}

function deleteSession(id) {
  fetch('/api/sessions/' + id, { method: 'DELETE' });
  if (currentSessionId === id) {
    currentSessionId = null;
    document.getElementById('chatArea').innerHTML = '<div class="empty"><div class="icon">Nezha</div><div>' + t('clickNewChat') + '</div></div>';
  }
  loadSessions();
}

function exportSession() {
  if (!currentSessionId) return;
  window.open('/api/sessions/' + currentSessionId + '/export');
}

function clearMemory() {
  if (!currentAgent) return;
  fetch('/api/agents/' + currentAgent + '/clear', { method: 'POST' });
  if (currentSessionId) selectSession(currentSessionId);
}

function loadAgents() {
  fetch('/api/agents').then(r => r.json()).then(data => {
    var list = document.getElementById('agentList');
    var pl = document.getElementById('pipelineAgents');
    list.innerHTML = '';
    pl.innerHTML = '';
    data.agents.forEach(a => {
      var div = document.createElement('div');
      div.className = 'agent-item' + (currentAgent === a.name && currentMode === 'single' ? ' active' : '');
      div.innerHTML = '<div class="name">' + a.name + '</div><div class="info">Memory: ' + a.memorySize + '</div>';
      div.onclick = () => selectAgent(a.name);
      list.appendChild(div);
      var tag = document.createElement('span');
      tag.className = 'pipeline-tag' + (selectedPipelineAgents[a.name] ? ' selected' : '');
      tag.textContent = a.name;
      tag.onclick = () => togglePipelineAgent(a.name);
      pl.appendChild(tag);
    });
  });
}

function selectAgent(name) {
  currentAgent = name;
  currentMode = 'single';
  document.getElementById('pipelineAgents').style.display = 'none';
  document.querySelectorAll('.mode-btn').forEach(b => b.classList.remove('active'));
  loadAgents();
}

function setMode(mode) {
  currentMode = mode;
  currentAgent = null;
  document.querySelectorAll('.mode-btn').forEach(b => b.classList.remove('active'));
  document.getElementById('btn-' + mode).classList.add('active');
  document.getElementById('pipelineAgents').style.display = mode === 'single' ? 'none' : 'flex';
  loadAgents();
}

function togglePipelineAgent(name) {
  if (selectedPipelineAgents[name]) delete selectedPipelineAgents[name];
  else selectedPipelineAgents[name] = true;
  loadAgents();
}

function addMessage(role, content, elapsed) {
  var area = document.getElementById('chatArea');
  if (area.querySelector('.empty')) area.innerHTML = '';
  var div = document.createElement('div');
  div.className = 'msg ' + role;
  var tc = document.createElement('div');
  tc.textContent = content;
  tc.className = 'bubble';
  div.appendChild(tc);
  if (elapsed) {
    var m = document.createElement('div');
    m.className = 'meta';
    m.textContent = elapsed + 'ms';
    div.appendChild(m);
  }
  area.appendChild(div);
  area.scrollTop = area.scrollHeight;
}

async function send() {
  var msg = document.getElementById('inputMsg').value.trim();
  if (!msg) return;
  var btn = document.getElementById('sendBtn');
  btn.disabled = true;
  if (currentMode === 'single' && !currentAgent) {
    alert(t('selectAgentFirst'));
    btn.disabled = false;
    return;
  }
  if (!currentSessionId) newSession();
  var sid = currentSessionId;
  while (!sid) {
    await new Promise(r => setTimeout(r, 100));
    sid = currentSessionId;
  }
  addMessage('user', msg);
  document.getElementById('inputMsg').value = '';
  var area = document.getElementById('chatArea');
  var ld = document.createElement('div');
  ld.className = 'msg assistant';
  ld.innerHTML = '<div class="bubble"><span class="loading"></span>' + t('thinking') + '</div>';
  area.appendChild(ld);
  area.scrollTop = area.scrollHeight;
  try {
    var body = { message: msg, sessionId: sid };
    var resp;
    if (currentMode === 'single') {
      body.agent = currentAgent;
      resp = await fetch('/api/chat', { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(body) });
    } else {
      var agents = Object.keys(selectedPipelineAgents);
      if (agents.length === 0) {
        alert(t('selectOneAgent'));
        btn.disabled = false;
        return;
      }
      body.agents = agents;
      resp = await fetch('/api/pipeline/' + currentMode, { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(body) });
    }
    ld.remove();
    var data = await resp.json();
    if (data.error) addMessage('assistant', t('error') + data.error);
    else addMessage('assistant', data.content, data.elapsed);
    loadSessions();
  } catch (e) {
    ld.remove();
    addMessage('assistant', t('networkError') + e.message);
  }
  btn.disabled = false;
  loadAgents();
}

function showAgentManager(editName) {
  var editing = !!editName;
  fetch('/api/models').then(r => r.json()).then(models => {
    var modelOptions = models.map(m => '<option value="' + m.name + '">' + m.name + ' (' + m.model + ')</option>').join('');
    var html = '<div class="modal-overlay" onclick="if(event.target===this)closeModal()"><div class="modal"><h2>' + (editing ? t('editAgent') : t('createAgent')) + '</h2>';
    html += '<label>' + t('agentName') + '</label><input id="m-name" placeholder="' + t('agentNamePh') + '">';
    html += '<label>' + t('sysPrompt') + '</label><textarea id="m-prompt" placeholder="' + t('sysPromptPh') + '"></textarea>';
    html += '<label>' + t('model') + '</label><select id="m-model">' + modelOptions + '</select>';
    html += '<div class="modal-actions"><button class="secondary" onclick="closeModal()">' + t('cancel') + '</button>';
    if (editing) html += '<button class="secondary" style="color:#e94560;border-color:#e94560" onclick="doDeleteAgent(\'' + editName + '\')">' + t('delete') + '</button>';
    html += '<button class="primary" onclick="doSaveAgent(\'' + (editName || '') + '\')">' + t('save') + '</button></div></div></div>';
    document.getElementById('modalContainer').innerHTML = html;
    if (editing) {
      document.getElementById('m-name').value = editName;
      document.getElementById('m-name').disabled = true;
    }
  });
}

function doSaveAgent(oldName) {
  var name = document.getElementById('m-name').value.trim();
  var prompt = document.getElementById('m-prompt').value;
  var model = document.getElementById('m-model').value;
  if (!name) { alert(t('nameRequired')); return; }
  var method = oldName ? 'PUT' : 'POST';
  var url = oldName ? '/api/agents/custom/' + oldName : '/api/agents/custom';
  fetch(url, { method: method, headers: { 'Content-Type': 'application/json' }, body: JSON.stringify({ name: name, sysPrompt: prompt, modelName: model }) })
    .then(r => r.json()).then(d => { closeModal(); loadAgents(); });
}

function doDeleteAgent(name) {
  if (!confirm(t('deleteConfirm'))) return;
  fetch('/api/agents/custom/' + name, { method: 'DELETE' }).then(() => { closeModal(); loadAgents(); });
}

function closeModal() { document.getElementById('modalContainer').innerHTML = ''; }

function showSettings() {
  fetch('/api/settings').then(r => r.json()).then(s => {
    var html = '<div class="modal-overlay" onclick="if(event.target===this)closeModal()"><div class="modal"><h2>' + t('settings') + '</h2>';
    html += '<label>' + t('deepseekKey') + '</label><input id="s-ds" value="' + esc(s.deepseekKey || '') + '">';
    html += '<label>' + t('openaiKey') + '</label><input id="s-oa" value="' + esc(s.openaiKey || '') + '">';
    html += '<label>' + t('claudeKey') + '</label><input id="s-cl" value="' + esc(s.claudeKey || '') + '">';
    html += '<label>' + t('qwenKey') + '</label><input id="s-qw" value="' + esc(s.qwenKey || '') + '">';
    html += '<div class="modal-actions"><button class="secondary" onclick="closeModal()">' + t('cancel') + '</button><button class="primary" onclick="doSaveSettings()">' + t('save') + '</button></div></div></div>';
    document.getElementById('modalContainer').innerHTML = html;
  });
}

function doSaveSettings() {
  var body = {
    deepseekKey: document.getElementById('s-ds').value,
    openaiKey: document.getElementById('s-oa').value,
    claudeKey: document.getElementById('s-cl').value,
    qwenKey: document.getElementById('s-qw').value
  };
  fetch('/api/settings', { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(body) }).then(() => closeModal());
}

function showMemory() {
  if (!currentAgent) return;
  fetch('/api/memory/' + currentAgent).then(r => r.json()).then(mems => {
    var html = '<div class="modal-overlay" onclick="if(event.target===this)closeModal()"><div class="modal" style="width:520px"><h2>' + t('memory') + ': ' + (currentAgent || '') + '</h2>';
    html += '<div style="margin-bottom:12px"><input id="mem-q" placeholder="' + t('searchMemory') + '" style="width:100%" oninput="searchMem()"></div>';
    html += '<div id="mem-list" style="max-height:300px;overflow-y:auto">';
    mems.forEach(m => {
      html += '<div style="padding:8px;border:1px solid var(--border);border-radius:6px;margin-bottom:4px;display:flex;justify-content:space-between"><div><span style="font-size:11px;opacity:0.5">' + esc(String(m.CREATED_AT || '')) + '</span><div style="font-size:13px;margin-top:2px">' + esc(String(m.CONTENT || '')) + '</div></div><span class="del" onclick="delMem(' + m.ID + ');event.stopPropagation()" style="cursor:pointer;color:var(--accent);font-size:18px">x</span></div>';
    });
    html += '</div><div class="modal-actions"><button class="secondary" onclick="closeModal()">' + t('cancel') + '</button><button class="primary" onclick="addMem()">' + t('add') + '</button></div></div></div>';
    document.getElementById('modalContainer').innerHTML = html;
  });
}

function searchMem() {
  var q = document.getElementById('mem-q').value;
  fetch('/api/memory/' + currentAgent + '/search?q=' + encodeURIComponent(q)).then(r => r.json()).then(mems => {
    var el = document.getElementById('mem-list');
    el.innerHTML = '';
    mems.forEach(m => {
      el.innerHTML += '<div style="padding:8px;border:1px solid var(--border);border-radius:6px;margin-bottom:4px;display:flex;justify-content:space-between"><div><span style="font-size:11px;opacity:0.5">' + esc(String(m.CREATED_AT || '')) + '</span><div style="font-size:13px;margin-top:2px">' + esc(String(m.CONTENT || '')) + '</div></div><span onclick="delMem(' + m.ID + ')" style="cursor:pointer;color:var(--accent);font-size:18px">x</span></div>';
    });
  });
}

function addMem() {
  var content = prompt(t('enterMemory'));
  if (!content) return;
  fetch('/api/memory', { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify({ agentName: currentAgent, content: content, contentType: 'fact', importance: 5 }) }).then(() => showMemory());
}

function delMem(id) {
  fetch('/api/memory/' + id, { method: 'DELETE' }).then(() => showMemory());
}

function showTasks() {
  fetch('/api/tasks').then(r => r.json()).then(tasks => {
    var html = '<div class="modal-overlay" onclick="if(event.target===this)closeModal()"><div class="modal" style="width:500px"><h2>' + t('scheduledTasks') + '</h2>';
    tasks.forEach(tk => {
      var en = tk.ENABLED;
      html += '<div style="padding:10px;border:1px solid var(--border);border-radius:6px;margin-bottom:6px;display:flex;justify-content:space-between;align-items:center">';
      html += '<div><div style="font-weight:600;font-size:13px">' + esc(String(tk.NAME || '')) + '</div><div style="font-size:11px;opacity:0.6">' + esc(String(tk.AGENT_NAME || '')) + ' | cron: ' + esc(String(tk.CRON_EXPR || '')) + '</div></div>';
      html += '<div style="display:flex;gap:6px"><button onclick="toggleTask(\'' + tk.ID + '\',' + (!en) + ')" style="padding:4px 8px;border-radius:4px;border:1px solid var(--border);background:' + (en ? 'var(--accent)' : 'transparent') + ';color:' + (en ? 'white' : 'var(--text)') + ';cursor:pointer;font-size:11px">' + (en ? 'ON' : 'OFF') + '</button>';
      html += '<span onclick="delTask(\'' + tk.ID + '\')" style="cursor:pointer;color:var(--accent);font-size:16px">x</span></div></div>';
    });
    html += '<div style="margin-top:12px;border-top:1px solid var(--border);padding-top:12px"><label>' + t('taskName') + '</label><input id="t-name" placeholder="' + t('taskNamePh') + '">';
    html += '<label>' + t('agent') + '</label><select id="t-agent">';
    if (currentAgent) html += '<option value="' + currentAgent + '">' + currentAgent + '</option>';
    html += '</select>';
    html += '<label>' + t('message') + '</label><textarea id="t-msg" rows="2"></textarea>';
    html += '<label>' + t('cron') + '</label><input id="t-cron" placeholder="' + t('cronPh') + '">';
    html += '<div class="modal-actions"><button class="secondary" onclick="closeModal()">' + t('cancel') + '</button><button class="primary" onclick="addTask()">' + t('add') + '</button></div></div></div></div>';
    document.getElementById('modalContainer').innerHTML = html;
  });
}

function addTask() {
  var name = document.getElementById('t-name').value;
  var agent = document.getElementById('t-agent').value;
  var msg = document.getElementById('t-msg').value;
  var cron = document.getElementById('t-cron').value;
  if (!name || !msg) { alert(t('nameMsgRequired')); return; }
  fetch('/api/tasks', { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify({ name: name, agentName: agent, message: msg, cronExpr: cron }) }).then(() => showTasks());
}

function delTask(id) {
  fetch('/api/tasks/' + id, { method: 'DELETE' }).then(() => showTasks());
}

function toggleTask(id, en) {
  fetch('/api/tasks/' + id + '/toggle', { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify({ enabled: en }) }).then(() => showTasks());
}

function compressCurrentSession() {
  if (!currentSessionId) { alert(t('selectFirst')); return; }
  if (!confirm(t('compressConfirm'))) return;
  fetch('/api/compress/' + currentSessionId, { method: 'POST' }).then(r => r.json()).then(d => {
    if (d.error) { alert(d.error); return; }
    alert(t('compressed') + ' ' + d.originalCount + ' -> ' + d.compressedCount + ' (saved ' + d.saved + ')');
    selectSession(currentSessionId);
  });
}

// Init
currentLang = localStorage.getItem('nezha-lang') || 'zh';
var savedTheme = localStorage.getItem('nezha-theme');
if (savedTheme) document.documentElement.setAttribute('data-theme', savedTheme);
applyLang();
loadSessions();
loadAgents();
</script>
</body>
</html>'''

base = r'C:\Users\yifan\.qclaw\workspace\nezha\src\main\resources\static'
with open(base + '\\index.html', 'w', encoding='utf-8') as f:
    f.write(html)
print('OK - index.html generated')
