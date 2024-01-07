package org.jxch.capital.http.ai.dto;

import java.util.List;

public interface TextAiParam {

    <T> T getParam(Class<T> clazz);

    TextAiParam addText(String text);

    List<String> chainText();

}
