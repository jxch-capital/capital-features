package org.jxch.capital.config;

import io.minio.MinioClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {
    private String url;
    private String accessKey;
    private String secretKey;

    @Bean
    public MinioClient minioClient(OkHttpClient okHttpClient) {
        return MinioClient.builder()
                .httpClient(okHttpClient)
                .credentials(accessKey, secretKey)
                .endpoint(Objects.requireNonNull(HttpUrl.parse(url)))
                .build();
    }

}
