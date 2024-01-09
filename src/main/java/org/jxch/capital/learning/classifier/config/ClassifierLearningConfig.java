package org.jxch.capital.learning.classifier.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "model.smile")
public class ClassifierLearningConfig {
    private String modelPath;
    private String modelSuffix;

    @PostConstruct
    public void init() {
        File path = new File(modelPath);
        if (!path.exists()) {
            boolean mk = path.mkdirs();
            log.info("创建模型目录：{}. [{}]", modelPath, mk);
        }
    }

}
