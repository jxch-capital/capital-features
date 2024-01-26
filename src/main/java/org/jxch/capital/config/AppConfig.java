package org.jxch.capital.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@EnableScheduling
@EnableTransactionManagement
public class AppConfig {

    @Bean
    @Primary
    public CacheManager cacheManager() {
        // todo 对接配置信息
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterAccess(30, TimeUnit.MINUTES)
                .maximumSize(2000));
        return cacheManager;
    }

    @Bean("cacheManagerShort")
    public CacheManager cacheManagerShort() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterAccess(3, TimeUnit.SECONDS)
                .maximumSize(10000));
        return cacheManager;
    }

}
