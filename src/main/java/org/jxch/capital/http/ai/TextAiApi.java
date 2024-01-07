package org.jxch.capital.http.ai;

import lombok.NonNull;
import org.jxch.capital.http.ai.dto.TextAiParam;
import org.jxch.capital.http.ai.dto.TextAiRes;

import java.util.List;

public interface TextAiApi {

    TextAiRes questionText(TextAiParam param);

    TextAiParam getDefaultParam();

    TextAiParam getParam(List<String> texts);

    default String questionTextToString(TextAiParam param) {
        return questionText(param).resText();
    }

    default TextAiParam questionTextChain(@NonNull TextAiParam param) {
        return param.addText(questionTextToString(param));
    }

}
