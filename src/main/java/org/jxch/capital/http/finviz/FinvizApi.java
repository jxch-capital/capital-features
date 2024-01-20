package org.jxch.capital.http.finviz;

import jakarta.annotation.Resource;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class FinvizApi {
    @Resource
    private OkHttpClient okHttpClient;
    @Resource
    @Qualifier("newsRequest")
    private Request newsRequest;


    private FinvizNewsDto trToFinvizNewsDto(@NonNull Element tr) {
        Elements td = tr.select("td");
        Element a = td.get(2).select("a").first();
        return FinvizNewsDto.builder()
                .date(td.get(1).text())
                .title(a != null ? a.text() : null)
                .url(a != null ? a.attr("href") : null)
                .build();
    }

    private List<FinvizNewsDto> getNewsDtoList(String html, String tableCss) {
        return Jsoup.parse(html).select(tableCss).select("tr").stream()
                .map(this::trToFinvizNewsDto).filter(FinvizNewsDto::isEffective).toList();
    }

    private List<FinvizNewsDto> getNews(String html) {
        return getNewsDtoList(html, "#news > div > table > tbody > tr:nth-child(2) > td:nth-child(1) > table > tbody");
    }

    private List<FinvizNewsDto> getBlogs(String html) {
        return getNewsDtoList(html, "#news > div > table > tbody > tr:nth-child(2) > td:nth-child(3) > table > tbody");
    }

    @SneakyThrows
    public List<FinvizNewsDto> news() {
        try (Response response = okHttpClient.newCall(newsRequest).execute()) {
            return getNews(Objects.requireNonNull(response.body()).string());
        }
    }

    @SneakyThrows
    public List<FinvizNewsDto> blogs() {
        try (Response response = okHttpClient.newCall(newsRequest).execute()) {
            return getBlogs(Objects.requireNonNull(response.body()).string());
        }
    }

    @SneakyThrows
    public List<FinvizNewsDto> allNews() {
        try (Response response = okHttpClient.newCall(newsRequest).execute()) {
            String html = Objects.requireNonNull(response.body()).string();
            ArrayList<FinvizNewsDto> allNewsDtoList = new ArrayList<>(getNews(html));
            allNewsDtoList.addAll(getBlogs(html));
            return allNewsDtoList;
        }
    }

}
