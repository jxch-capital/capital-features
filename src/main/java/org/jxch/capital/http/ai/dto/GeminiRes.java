package org.jxch.capital.http.ai.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

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
        return this.candidates.get(0).getContent().getParts().get(0).getText();
    }

}
