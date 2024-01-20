package org.jxch.capital.http.driver;

import lombok.Data;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.ClientConfig;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.net.URL;
import java.util.HashMap;

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
    @Bean(destroyMethod = "quit", name = "chromeRemoteDriver")
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public WebDriver chromeRemoteDriver(@NonNull ChromeOptions chromeOptions) {
        try {
            ClientConfig clientConfig = ClientConfig.defaultConfig();
            HttpCommandExecutor executor = new HttpCommandExecutor(new HashMap<>(), new URL(webDriverHttp), clientConfig);
            return new RemoteWebDriver(executor, chromeOptions);
        } catch (Throwable e) {
            return null;
        }
    }

}
