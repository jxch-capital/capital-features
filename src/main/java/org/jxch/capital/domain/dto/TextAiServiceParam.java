package org.jxch.capital.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.server.TextAiEnum;

import java.util.List;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TextAiServiceParam {
    private List<String> texts;
    private TextAiEnum textAiEnum;
    private Long roleId;
    @Builder.Default
    private int length = 5;

    public TextAiServiceParam addFirstText(String text) {
        texts.add(0, text);
        return this;
    }

}
