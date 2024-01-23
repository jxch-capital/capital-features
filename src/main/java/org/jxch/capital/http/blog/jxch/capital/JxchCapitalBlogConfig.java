package org.jxch.capital.http.blog.jxch.capital;

import lombok.Data;
import okhttp3.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class JxchCapitalBlogConfig {
    private final String directoryUrl = "https://jxch-capital.github.io/2024/01/23/%E6%96%87%E7%AB%A0%E7%89%87%E6%AE%B5/%E4%BB%B7%E6%A0%BC%E8%A1%8C%E4%B8%BA%E6%96%87%E7%AB%A0%E7%89%87%E6%AE%B5%E7%9B%AE%E5%BD%95/";

    @Bean
    public Request jxchCapitalAllPriceActionBlogsRequest() {
        return new Request.Builder()
                .url("https://jxch-capital.github.io/2024/01/23/%E6%96%87%E7%AB%A0%E7%89%87%E6%AE%B5/%E4%BB%B7%E6%A0%BC%E8%A1%8C%E4%B8%BA%E6%96%87%E7%AB%A0%E7%89%87%E6%AE%B5%E7%9B%AE%E5%BD%95/")
                .get()
                .build();
    }

}
