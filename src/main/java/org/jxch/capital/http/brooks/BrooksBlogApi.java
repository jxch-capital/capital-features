package org.jxch.capital.http.brooks;

import jakarta.annotation.Resource;
import lombok.NonNull;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jxch.capital.http.brooks.dto.BrooksBlogParam;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Setter
@Slf4j
@Service
public class BrooksBlogApi {
    @Resource
    private OkHttpClient okHttpClient;
    @Resource
    @Qualifier("brooksBlogRequest")
    private Function<Integer, Request> brooksBlogRequest;
    @Resource
    @Qualifier("brooksBlogArticleRequest")
    private Function<String, Request> brooksBlogArticleRequest;

    @NonNull
    @SneakyThrows
    public List<String> articleUrlsInPage(@NonNull BrooksBlogParam param) {
        try (Response response = okHttpClient.newCall(brooksBlogRequest.apply(param.getPage())).execute()) {
            return Jsoup.parse(Objects.requireNonNull(response.body()).string())
                    .select("main.content article").stream()
                    .filter(article -> param.getContainsPinned() || !article.hasClass("sticky"))
                    .map(article -> article.selectFirst("header.entry-header"))
                    .filter(Objects::nonNull)
                    .map(header -> header.selectFirst("h2.entry-title[itemprop=headline]"))
                    .filter(Objects::nonNull)
                    .map(h2 -> h2.selectFirst("a.entry-title-link"))
                    .filter(Objects::nonNull)
                    .map(a -> a.attr("href"))
                    .collect(Collectors.toList());
        }
    }

    public String topArticleUrl(int index) {
        return Optional.ofNullable(articleUrlsInPage(BrooksBlogParam.builder().page(1).containsPinned(false).build()).get(index))
                .orElseThrow(() -> new RuntimeException("找不到文章"));
    }

    @NonNull
    public String newArticleUrl() {
        return topArticleUrl(0);
    }

    @NonNull
    @SneakyThrows
    public String articleHtml(@NonNull String url) {
        try (Response response = okHttpClient.newCall(brooksBlogArticleRequest.apply(url)).execute()) {
            return Optional.ofNullable(Jsoup.parse(Objects.requireNonNull(response.body()).string())
                            .selectFirst("main.content article"))
                    .orElseThrow(() -> new IllegalArgumentException("这个URL中不包含文章: " + url))
                    .html();
        }
    }

    public String newArticleHtml() {
        for (int i = 0; i < 20; i++) {
            String articleHtml = articleHtml(topArticleUrl(i));
            if (articleHtml.contains("Please log in to view content.")) {
                continue;
            }
            return articleHtml;
        }

        throw new IllegalArgumentException("前20篇文章都需要登录才能访问，请核实文章源是否有效");
    }

}
