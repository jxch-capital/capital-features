package org.jxch.capital.http.ai;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "ai.gemini")
public class GeminiConfig {
    private boolean useProxy = false;
    private String proxyHost;
    private Integer proxyPort;
    private String key;

    @Bean("geminiClient")
    public OkHttpClient geminiClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (useProxy) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
            builder.proxy(proxy);
        }

        return builder
                .readTimeout(10, TimeUnit.MINUTES)
                .writeTimeout(10, TimeUnit.MINUTES)
                .callTimeout(10, TimeUnit.MINUTES)
                .build();
    }

    @Bean("geminiRequestBuilder")
    public Supplier<Request.Builder> geminiRequestBuilder() {
        return () -> new Request.Builder()
                .url("https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=" + key)
                .addHeader("Content-Type", "application/json");
    }


}
