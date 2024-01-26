package org.jxch.capital.config;

import io.minio.MinioClient;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import org.jxch.capital.utils.FileU;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "io")
public class IOConfig {
    private MinioConfig minio;
    private String namespace;

    @Bean
    public MinioClient minioClient(OkHttpClient okHttpClient) {
        return MinioClient.builder()
                .httpClient(okHttpClient)
                .credentials(minio.getAccessKey(), minio.getSecretKey())
                .endpoint(Objects.requireNonNull(HttpUrl.parse(minio.getUrl())))
                .build();
    }

    @PostConstruct
    public void init() {
        FileU.mkdirIfNotExists(minio.getLocalPath());
    }


}
