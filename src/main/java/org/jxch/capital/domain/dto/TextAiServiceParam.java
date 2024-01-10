package org.jxch.capital.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.server.TextAiEnum;

import java.util.List;
import java.util.Objects;

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
    private String knowledge;

    public TextAiServiceParam addFirstText(String text) {
        texts.add(0, text);
        return this;
    }

    public TextAiServiceParam addLastText(String text) {
        this.texts.add(text);
        return this;
    }

    public boolean hasKnowledge() {
        return Objects.nonNull(knowledge) && !knowledge.isBlank() && !knowledge.isEmpty();
    }

    public String getLastText() {
        return texts.get(texts.size() - 1);
    }

}
