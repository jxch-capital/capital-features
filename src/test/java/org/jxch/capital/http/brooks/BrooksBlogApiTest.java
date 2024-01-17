package org.jxch.capital.http.brooks;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class BrooksBlogApiTest {
    @Autowired
    private BrooksBlogApi brooksBlogApi;

    @Test
    void articleUrlsInPage() {
    }

    @Test
    void newArticleUrlInPage() {
        String url = brooksBlogApi.newArticleUrl();
        log.info(url);
    }

}