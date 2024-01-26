package org.jxch.capital.config;

import dev.langchain4j.store.embedding.milvus.MilvusEmbeddingStore;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "milvus")
public class MilvusConfig {
    private String url;
    private Integer dimension;

//    @Bean
    public MilvusEmbeddingStore milvusEmbeddingStore() {
        HttpUrl httpUrl = Optional.ofNullable(HttpUrl.parse(url)).orElseThrow(() -> new IllegalArgumentException("无效的URL: " + url));
        return MilvusEmbeddingStore.builder()
                .host(httpUrl.host())
                .port(httpUrl.port())
                .dimension(dimension)
                .build();
    }

}
