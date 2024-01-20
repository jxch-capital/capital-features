package org.jxch.capital.http.ai.coze;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.optim.linear.UnboundedSolutionException;
import org.jxch.capital.http.ai.TextAiApi;
import org.jxch.capital.http.ai.TextAiParam;
import org.jxch.capital.http.ai.TextAiRes;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Service
public class CozeBotApi implements TextAiApi {

    @Override
    public TextAiRes questionText(@NonNull TextAiParam param) {
        CozeTextAiParam cozeTextAiParam = param.getParam(CozeTextAiParam.class);
        cozeTextAiParam.getWebDriver().get(cozeTextAiParam.getBotUrl());
        String title = cozeTextAiParam.getWebDriver().getCurrentUrl();

        return CozeTextAiRes.builder().resText(title).build();
    }

    @Override
    public TextAiRes questionTextStream(TextAiParam param, Consumer<String> read) {
        throw new UnboundedSolutionException();
    }

    @Override
    public TextAiParam getDefaultParam() {
        return new CozeTextAiParam();
    }

    @Override
    public TextAiParam getParam(List<String> texts) {
        return new CozeTextAiParam().setTexts(texts);
    }

}
