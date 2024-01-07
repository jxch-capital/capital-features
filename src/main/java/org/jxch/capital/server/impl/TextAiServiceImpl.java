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
import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class TextAiServiceImpl implements TextAiService {

    @NonNull
    private List<String> getParamTexts(@NonNull TextAiServiceParam param) {
        if (param.getTexts().isEmpty() || (param.getTexts().size() == 1 && param.getTexts().get(0).isBlank())) {
            throw new IllegalArgumentException("提问为空");
        }

        List<String> texts = param.getTexts();
        if (texts.size() > param.getLength()) {
            texts = texts.subList(texts.size() - param.getLength(), texts.size() - 1);
        }

        return texts;
    }

    @NonNull
    private TextAiApi getTextAiApi(@NonNull TextAiServiceParam param) {
        return AppContextHolder.getContext().getBean(param.getTextAiEnum().getTextAiApi());
    }

    @Override
    public List<String> question(@NonNull TextAiServiceParam param) {
        TextAiApi textAiApi = getTextAiApi(param);
        return textAiApi.questionTextToChainString(textAiApi.getParam(getParamTexts(param)));
    }

    @Override
    public List<String> questionStream(TextAiServiceParam param, Consumer<String> read) {
        TextAiApi textAiApi = getTextAiApi(param);
        return textAiApi.questionTextToChainStringStream(textAiApi.getParam(getParamTexts(param)), read);
    }

}
