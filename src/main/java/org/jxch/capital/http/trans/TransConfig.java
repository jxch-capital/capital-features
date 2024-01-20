package org.jxch.capital.http.trans;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "trans")
public class TransConfig {
    private String url;
    private int retry = 3;
    private boolean ignoredErrorTrans = true;
}
