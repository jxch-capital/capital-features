package org.jxch.capital.server;

import lombok.NonNull;
import org.jxch.capital.domain.dto.TextAiServiceParam;

import java.util.List;
import java.util.function.Consumer;

public interface TextAiService {


    List<String> question(TextAiServiceParam param);

    List<String> questionStream(TextAiServiceParam param, Consumer<String> read);

    default List<String> question(List<String> texts) {
        return question(TextAiServiceParam.builder()
                .texts(texts)
                .textAiEnum(TextAiEnum.GEMINI_PRO)
                .build());
    }

    default List<String> questionStream(List<String> texts, Consumer<String> read) {
        return questionStream(TextAiServiceParam.builder()
                .texts(texts)
                .textAiEnum(TextAiEnum.GEMINI_PRO)
                .build(), read);
    }

    default String questionLastRes(List<String> texts) {
        List<String> resList = question(TextAiServiceParam.builder()
                .texts(texts)
                .textAiEnum(TextAiEnum.GEMINI_PRO)
                .build());
        return resList.get(resList.size() - 1);
    }

    default String questionLastResStream(List<String> texts, Consumer<String> read) {
        List<String> resList = questionStream(TextAiServiceParam.builder()
                .texts(texts)
                .textAiEnum(TextAiEnum.GEMINI_PRO)
                .build(), read);
        return resList.get(resList.size() - 1);
    }

    default String questionLastRes(@NonNull TextAiServiceParam param) {
        List<String> resList = question(TextAiServiceParam.builder()
                .texts(param.getTexts())
                .textAiEnum(param.getTextAiEnum())
                .build());
        return resList.get(resList.size() - 1);
    }

    default String questionLastResStream(@NonNull TextAiServiceParam param, Consumer<String> read) {
        List<String> resList = questionStream(TextAiServiceParam.builder()
                .texts(param.getTexts())
                .textAiEnum(param.getTextAiEnum())
                .build(), read);
        return resList.get(resList.size() - 1);
    }

}
