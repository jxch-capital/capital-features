package org.jxch.capital.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jxch.capital.http.ai.GeminiApi;
import org.jxch.capital.http.ai.TextAiApi;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum TextAiEnum {
    GEMINI_PRO("Gemini-Pro", GeminiApi.class),
    ;
    private final String name;
    private final Class<? extends TextAiApi> textAiApi;

    public static TextAiEnum valueOfName(String name) {
        return Arrays.stream(values()).filter(textAiEnum -> Objects.equals(textAiEnum.name, name))
                .findAny().orElseThrow(() -> new IllegalArgumentException("没有这种AI的支持：" + name));
    }

}
