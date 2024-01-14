package org.jxch.capital.http.vec;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "vec.db")
public class VecDbConfig {
    private String scheme;
    private String host;
    private Integer port;
    private String path;
    private String dbUrl;

    @Bean
    public OkHttpClient vecDbClient() {
        return new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.MINUTES)
                .writeTimeout(10, TimeUnit.MINUTES)
                .callTimeout(10, TimeUnit.MINUTES)
                .build();
    }

    @Bean
    public Supplier<HttpUrl.Builder> vecDbUrlBuilder() {
        return () -> new HttpUrl.Builder()
                .scheme(scheme)
                .host(host)
                .port(port)
                .addPathSegments(path);
    }

}
