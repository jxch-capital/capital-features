package org.jxch.capital.server.impl;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@Slf4j
@SpringBootTest
class TextAiServiceImplTest {
    @Autowired
    private TextAiServiceImpl textAiService;

    @Test
    void question() {
        List<String> questions = textAiService.question(Arrays.asList("你是谁"));
        questions.add("为什么");
        questions = textAiService.question(questions);

        log.info(JSONObject.toJSONString(questions));

    }

    @Test
    void testQuestion() {
        List<String> questions = textAiService.questionStream(Arrays.asList("你是谁"), log::info);
        questions.add("为什么");
        questions = textAiService.questionStream(questions, log::info);

        log.info(JSONObject.toJSONString(questions));
    }
}