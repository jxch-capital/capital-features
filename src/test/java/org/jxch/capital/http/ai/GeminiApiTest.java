package org.jxch.capital.http.ai;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.http.ai.dto.TextAiParam;
import org.jxch.capital.http.ai.dto.TextAiRes;
import org.mapstruct.ap.internal.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class GeminiApiTest {
    @Autowired
    private GeminiApi geminiApi;

    @Test
    void t() {
        log.info("test");
    }

    @Test
    void questionText() {

        TextAiParam textAiParam = geminiApi.questionTextChain(geminiApi.getDefaultParam().addText("你是谁"));
        textAiParam.addText("你是那家公司研发的");
        textAiParam = geminiApi.questionTextChain(textAiParam);
        textAiParam.addText("你的知识更新与什么时间");
        textAiParam = geminiApi.questionTextChain(textAiParam);

        log.info(JSONObject.toJSONString(textAiParam.chainText()));
    }

    @Test
    void questionTextStream() {
        TextAiRes textAiRes = geminiApi.questionTextStream(
                geminiApi.getDefaultParam().addText("SpringBoot如何快速集成MIN-IO"),
                log::info);

        log.info(JSONObject.toJSONString(textAiRes.resText()));
    }

    @Test
    void testQuestionText() {
        TextAiParam textAiParam = geminiApi.questionTextChain(geminiApi.getDefaultParam()
                .addText("英译中,注意炒股及金融领域的专用术语的翻译准确性，以及常见缩写的翻译（金融术语及标的名称除外）")
                .addText("The Emini is going sideways at the December 28th high of last year. The bears want a downside breakout and test of the neckline (January low).")
        );
        textAiParam = geminiApi.questionTextChain(textAiParam);
        log.info(JSONObject.toJSONString(Collections.last(textAiParam.chainText())));

    }
}