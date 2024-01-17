package org.jxch.capital.http.brooks;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "brooks")
public class BrooksBlogConfig {
    private String blog = "https://www.brookstradingcourse.com/price-action-trading-blog/page/";
    private String cookie = "shield-notbot-nonce=afb8947417";

    @Bean
    public Function<Integer, Request> brooksBlogRequest() {
        return (page) -> new Request.Builder()
                .url(blog + page)
                .addHeader("Cookie", cookie)
                .get()
                .build();
    }

    @Bean("brooksBlogArticleRequest")
    public Function<String, Request> brooksBlogArticleRequest() {
        return (url) -> new Request.Builder()
                .url(url)
                .addHeader("Cookie", cookie)
                .get()
                .build();
    }

}
