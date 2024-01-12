package org.jxch.capital.chart;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "chart")
public class ChartConfig {
    private String pngPath;

    @PostConstruct
    public void init() {
        File file = new File(pngPath);
        if (!file.exists()) {
            if (file.mkdirs()) {
                log.info("创建成功：{}", pngPath);
            } else {
                log.error("创建失败：{}", pngPath);
            }
        }

        Charts.setPngPath(pngPath);
    }

}
