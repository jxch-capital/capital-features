package org.jxch.capital.http.ai.coze;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.http.ai.TextAiRes;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CozeTextAiRes implements TextAiRes {
    private String resText;

    @Override
    public String resText() {
        return resText;
    }

}
