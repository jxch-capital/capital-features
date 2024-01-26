package org.jxch.capital.config;

import lombok.Data;

@Data
public class MinioConfig {
    private String url;
    private String accessKey;
    private String secretKey;
    private String localPath;
}
