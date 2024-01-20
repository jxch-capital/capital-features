package org.jxch.capital.http.ai.coze;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.http.ai.TextAiRes;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CozeBotApiTest {
    @Autowired
    private CozeBotApi cozeBotApi;
    @Autowired
    private WebDriver chromeRemoteDriver;

    @Test
    void questionText() {
        try{
            CozeTextAiParam param = CozeTextAiParam.builder()
                    .webDriver(chromeRemoteDriver)
                    .botUrl("https://www.coze.com/space/7325943033676087314/bot/7325924369644027906")
                    .build();
            TextAiRes textAiRes = cozeBotApi.questionText(param);
            log.info(textAiRes.resText());
        } finally {
            chromeRemoteDriver.quit();
        }
    }
}