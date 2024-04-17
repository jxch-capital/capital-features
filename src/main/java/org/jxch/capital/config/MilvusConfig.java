package org.jxch.capital.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Data
@Slf4j
@Configuration
public class MilvusConfig {
    private String url;
    private Integer dimension;
// 4j
//    @Bean
//    public MilvusEmbeddingStore milvusEmbeddingStore() {
//        HttpUrl httpUrl = Optional.ofNullable(HttpUrl.parse(url)).orElseThrow(() -> new IllegalArgumentException("无效的URL: " + url));
//        return MilvusEmbeddingStore.builder()
//                .host(httpUrl.host())
//                .port(httpUrl.port())
//                .dimension(dimension)
//                .build();
//    }

}
