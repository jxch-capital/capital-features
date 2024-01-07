package org.jxch.capital.server;

import lombok.NonNull;
import org.jxch.capital.domain.dto.TextAiServiceParam;

import java.util.List;

public interface TextAiService {


    List<String> question(TextAiServiceParam param);

    default List<String> question(List<String> texts) {
        return question(TextAiServiceParam.builder()
                .texts(texts)
                .textAiEnum(TextAiEnum.GEMINI_PRO)
                .build());
    }

    default String questionLastRes(List<String> texts) {
        List<String> resList = question(TextAiServiceParam.builder()
                .texts(texts)
                .textAiEnum(TextAiEnum.GEMINI_PRO)
                .build());
        return resList.get(resList.size() - 1);
    }

    default String questionLastRes(@NonNull TextAiServiceParam param) {
        List<String> resList = question(TextAiServiceParam.builder()
                .texts(param.getTexts())
                .textAiEnum(param.getTextAiEnum())
                .build());
        return resList.get(resList.size() - 1);
    }

}
