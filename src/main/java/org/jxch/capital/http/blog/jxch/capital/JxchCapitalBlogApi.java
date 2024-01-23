package org.jxch.capital.http.blog.jxch.capital;

import jakarta.annotation.Resource;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jxch.capital.http.blog.dto.BlogTitle;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Random;

@Slf4j
@Service
public class JxchCapitalBlogApi {
    @Resource
    private OkHttpClient okHttpClient;
    @Resource
    @Qualifier("jxchCapitalAllPriceActionBlogsRequest")
    private Request jxchCapitalAllPriceActionBlogsRequest;

    @SneakyThrows
    public List<BlogTitle> allPriceActionBlogs() {
        try (Response response = okHttpClient.newCall(jxchCapitalAllPriceActionBlogsRequest).execute()) {
            return Jsoup.parse(Objects.requireNonNull(response.body()).string())
                    .select("#article-content").select("a").stream()
                    .map(element -> BlogTitle.builder().title(element.text()).url(element.attr("href")).build())
                    .toList();
        }
    }

    public BlogTitle randomPriceActionBlog() {
        List<BlogTitle> blogTitles = allPriceActionBlogs();
        return blogTitles.get(new Random().nextInt(blogTitles.size()));
    }


    public String blogHtmlContent(@NonNull BlogTitle blog) {
        return blogHtmlContent(blog, true);
    }

    @SneakyThrows
    public String blogHtmlContent(@NonNull BlogTitle blog, boolean removeId) {
        try (Response response = okHttpClient.newCall(new Request.Builder().url(blog.getUrl()).get().build()).execute()) {
            Elements content = Jsoup.parse(Objects.requireNonNull(response.body()).string())
                    .select("#article-content");

            if (removeId) {
                content.removeAttr("id");
                content.forEach(element -> element.removeAttr("id"));
            }

            return content.toString();
        }
    }

}
