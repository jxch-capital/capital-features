package org.jxch.capital.server.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.TextAiServiceParam;
import org.jxch.capital.http.ai.TextAiApi;
import org.jxch.capital.server.TextAiService;
import org.jxch.capital.utils.AppContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TextAiServiceImpl implements TextAiService {

    @Override
    public List<String> question(@NonNull TextAiServiceParam param) {
        if (param.getTexts().isEmpty() || (param.getTexts().size() == 1 && param.getTexts().get(0).isBlank())) {
            throw new IllegalArgumentException("提问为空");
        }

        TextAiApi textAiApi = AppContextHolder.getContext().getBean(param.getTextAiEnum().getTextAiApi());
        return textAiApi.questionTextToChainString(textAiApi.getParam(param.getTexts()));
    }

}
