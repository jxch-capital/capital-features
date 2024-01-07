package org.jxch.capital.http.ai;

import lombok.NonNull;
import org.jxch.capital.http.ai.dto.TextAiParam;
import org.jxch.capital.http.ai.dto.TextAiRes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public interface TextAiApi {

    TextAiRes questionText(TextAiParam param);

    TextAiRes questionTextStream(TextAiParam param, Consumer<String> read);

    TextAiParam getDefaultParam();

    TextAiParam getParam(List<String> texts);

    default String questionTextToString(TextAiParam param) {
        return questionText(param).resText();
    }

    default List<String> questionTextToChainString(TextAiParam param) {
        String resText = questionText(param).resText();
        List<String> texts = new ArrayList<>(param.chainText());
        texts.add(resText);
        return texts;
    }

    default List<String> questionTextToChainStringStream(TextAiParam param, Consumer<String> read) {
        String resText = questionTextStream(param, read).resText();
        List<String> texts = new ArrayList<>(param.chainText());
        texts.add(resText);
        return texts;
    }

    default TextAiParam questionTextChain(@NonNull TextAiParam param) {
        return param.addText(questionTextToString(param));
    }

}
