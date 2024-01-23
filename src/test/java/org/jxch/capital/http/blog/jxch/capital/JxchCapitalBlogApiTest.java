package org.jxch.capital.http.blog.jxch.capital;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.http.blog.dto.BlogTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class JxchCapitalBlogApiTest {
    @Autowired
    private JxchCapitalBlogApi jxchCapitalBlogApi;

    @Test
    void allBlogs() {
        List<BlogTitle> blogTitles = jxchCapitalBlogApi.allPriceActionBlogs();
        log.info(JSONObject.toJSONString(blogTitles));
    }

    @Test
    void randomBlog() {
        BlogTitle blogTitle = jxchCapitalBlogApi.randomPriceActionBlog();
        log.info(JSONObject.toJSONString(blogTitle));
    }

    @Test
    void blogHtmlContent() {
        String html = jxchCapitalBlogApi.blogHtmlContent(jxchCapitalBlogApi.randomPriceActionBlog());
        log.info(html);
    }
}