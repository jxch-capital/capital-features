package org.jxch.capital.server.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.http.ai.TextAiParam;
import org.jxch.capital.http.ai.gemini.GeminiApi;
import org.jxch.capital.server.GeminiService;
import org.jxch.capital.utils.CollU;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeminiServiceImpl implements GeminiService {
    private final GeminiApi geminiApi;

    @Override
    public String qa1(String q, String prompt) {
        TextAiParam textAiParam = geminiApi.getDefaultParam().addText(prompt).addText(q);
        return CollU.last(geminiApi.questionTextChain(textAiParam).chainText());
    }

}
