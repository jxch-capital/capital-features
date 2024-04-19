package org.jxch.capital.config;

import cn.hutool.extra.spring.SpringUtil;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.InfluxDBClientOptions;
import lombok.Data;
import okhttp3.OkHttpClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@Data
@Configuration
@ConfigurationProperties(prefix = "influx")
public class InfluxDBConfig {
    private String url;
    private String username;
    private String password;
    private String token;
    private String org;
    private String bucket;
    private Integer concurrency;
    private Integer batch;

    @Bean
    public InfluxDBClient influxDBClient() {
        InfluxDBClientOptions options = InfluxDBClientOptions.builder()
                .url(url)
                .bucket(bucket)
                .org(org)
                .authenticateToken(token.toCharArray())
                .okHttpClient(new OkHttpClient.Builder()
                        .readTimeout(10, TimeUnit.MINUTES)
                        .writeTimeout(10, TimeUnit.MINUTES)
                        .connectTimeout(10, TimeUnit.MINUTES)
                        .callTimeout(10, TimeUnit.MINUTES)
                ).build();
        return InfluxDBClientFactory.create(options);
    }

    @Bean
    public Semaphore influxWriteSemaphore() {
        return new Semaphore(concurrency);
    }

    public Semaphore getInfluxWriteSemaphore() {
        return SpringUtil.getBean("influxWriteSemaphore", Semaphore.class);
    }

}
