package org.jxch.capital.http.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.Duration;

@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "http")
public class HttpConfig {
    private boolean useProxy = false;
    private String proxyHost;
    private Integer proxyPort;

    @Bean
    @Primary
    public OkHttpClient okHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (useProxy) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
            builder.proxy(proxy);
        }

        return builder
                .connectTimeout(Duration.ofMinutes(3))
                .writeTimeout(Duration.ofMinutes(3))
                .readTimeout(Duration.ofMinutes(3))
                .build();
    }


}
