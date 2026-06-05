package com.nezha;

import java.util.List;

public interface LLMModel {

    List<Msg> chat(String sysPrompt, List<Msg> history);
}
