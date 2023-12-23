package org.jxch.capital.yahoo;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.function.Supplier;

@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "yahoo")
public class YahooConfig {
    private String cookie;
    private String userAgent;
    private String crumb;
    private boolean useProxy;
    private String proxyHost;
    private Integer proxyPort;

    @Bean
    public OkHttpClient yahooClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (useProxy) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
            builder.proxy(proxy);
        }

        return builder.build();
    }

    @Bean
    public Supplier<HttpUrl.Builder> newYahooUrlBuilder() {
        return () -> new HttpUrl.Builder()
                .scheme("https")
                .addQueryParameter("crumb", crumb);
    }

    @Bean
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Supplier<Request.Builder> newYahooRequestBuilder() {
        return () -> new Request.Builder()
                .addHeader("cookie", cookie)
                .addHeader("user-agent", userAgent);
    }

    @Bean
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Supplier<HttpUrl.Builder> newYahooQuoteUrlBuilder(@NonNull Supplier<HttpUrl.Builder> newYahooUrlBuilder) {
        return () -> newYahooUrlBuilder.get()
                .host("query1.finance.yahoo.com")
                .addPathSegments("/v7/finance/quote");
    }

    @Bean
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Supplier<HttpUrl.Builder> newYahooDownloadStockCsvUrlBuilder(@NonNull Supplier<HttpUrl.Builder> newYahooUrlBuilder) {
        return () -> newYahooUrlBuilder.get()
                .host("query1.finance.yahoo.com")
                .addPathSegments("/v7/finance/download/");
    }


}
