package org.jxch.capital.http.yahoo;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jxch.capital.http.config.RetrySuccessInterceptor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.Duration;
import java.util.function.Supplier;

@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "yahoo")
public class YahooConfig {
    private String cookie;
    private String userAgent;
    private String crumb;
    private boolean useProxy = false;
    private String proxyHost;
    private Integer proxyPort;

    @Bean
    public OkHttpClient yahooClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (useProxy) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
            builder.proxy(proxy);
        }

        return builder
                .addInterceptor(RetrySuccessInterceptor.builder().maxRetry(3).retryWait(Duration.ofMillis(500)).build())
                .build();
    }

    @Bean(name = "newYahooUrlBuilder")
    public Supplier<HttpUrl.Builder> newYahooUrlBuilder() {
        return () -> new HttpUrl.Builder()
                .scheme("https")
                .addQueryParameter("crumb", crumb);
    }

    @Bean(name = "newYahooRequestBuilder")
    public Supplier<Request.Builder> newYahooRequestBuilder() {
        return () -> new Request.Builder()
                .addHeader("cookie", cookie)
                .addHeader("user-agent", userAgent);
    }

    @Bean(name = "newYahooQuoteUrlBuilder")
    public Supplier<HttpUrl.Builder> newYahooQuoteUrlBuilder(@NonNull @Qualifier("newYahooUrlBuilder") Supplier<HttpUrl.Builder> newYahooUrlBuilder) {
        return () -> newYahooUrlBuilder.get()
                .host("query1.finance.yahoo.com")
                .addPathSegments("/v7/finance/quote");
    }

    @Bean(name = "newYahooDownloadStockCsvUrlBuilder")
    public Supplier<HttpUrl.Builder> newYahooDownloadStockCsvUrlBuilder(@NonNull @Qualifier("newYahooUrlBuilder") Supplier<HttpUrl.Builder> newYahooUrlBuilder) {
        return () -> newYahooUrlBuilder.get()
                .host("query1.finance.yahoo.com")
                .addPathSegments("/v7/finance/download/");
    }

    @Bean(name = "newYahooChartUrlBuilder")
    public Supplier<HttpUrl.Builder> newYahooChartUrlBuilder(@NonNull @Qualifier("newYahooUrlBuilder") Supplier<HttpUrl.Builder> newYahooUrlBuilder) {
        return () -> newYahooUrlBuilder.get()
                .host("query2.finance.yahoo.com")
                .addPathSegments("/v8/finance/chart/");
    }


}
