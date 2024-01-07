package org.jxch.capital.http.ai.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GeminiRes implements TextAiRes {
    private List<GeminiDto> candidates;
    private PromptFeedback promptFeedback;

    @Override
    public String resText() {
        return getResString();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class PromptFeedback {
        private List<GeminiDto.SafetySetting> promptFeedbacks;
    }

    @JSONField(serialize = false)
    public String getResString() {
        if (Objects.isNull(candidates) || candidates.isEmpty() || Objects.isNull(candidates.get(0).getContent())) {
            return "NULL";
        }

        return this.candidates.get(0).getContent().getParts().get(0).getText();
    }

}
