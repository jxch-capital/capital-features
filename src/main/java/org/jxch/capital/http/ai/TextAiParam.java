package org.jxch.capital.http.ai;

import lombok.NonNull;

import java.util.List;

public interface TextAiParam {

    default <T> T getParam(@NonNull Class<T> clazz) {
        return clazz.cast(this);
    }

    TextAiParam addText(String text);

    List<String> chainText();

}
