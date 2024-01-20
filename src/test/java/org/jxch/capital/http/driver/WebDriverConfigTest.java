package org.jxch.capital.http.driver;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class WebDriverConfigTest {
    @Autowired
    private WebDriverConfig webDriverConfig;
    @Autowired
    private WebDriver webDriver;

    @Test
    void webDriver() {
        webDriver.get("http://www.baidu.com");
        log.info(webDriver.getTitle());
    }
}