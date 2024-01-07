package org.jxch.capital.http.ai;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.http.ai.dto.TextAiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class GeminiApiTest {
    @Autowired
    private GeminiApi geminiApi;

    @Test void t() {
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
}