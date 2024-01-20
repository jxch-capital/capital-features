package org.jxch.capital.http.driver;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.net.URL;

@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "driver")
public class WebDriverConfig {
    private String webDriverHttp;

    @Bean
    public ChromeOptions chromeOptions() {
        return new ChromeOptions().addArguments("--incognito", "--start-maximized");
    }

    @SneakyThrows
    @Bean(destroyMethod = "quit")
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public WebDriver chromeDriver(ChromeOptions chromeOptions) {
        return new RemoteWebDriver(new URL(webDriverHttp), chromeOptions);
    }

}
