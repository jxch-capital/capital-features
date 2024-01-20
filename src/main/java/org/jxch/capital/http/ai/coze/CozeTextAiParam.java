package org.jxch.capital.http.ai.coze;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.http.ai.TextAiParam;
import org.openqa.selenium.WebDriver;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CozeTextAiParam implements TextAiParam {
    private String botUrl;
    private WebDriver webDriver;
    private String question;
    private List<String> texts;

    @Override
    public TextAiParam addText(String text) {
        return null;
    }

    @Override
    public List<String> chainText() {
        return null;
    }

}
