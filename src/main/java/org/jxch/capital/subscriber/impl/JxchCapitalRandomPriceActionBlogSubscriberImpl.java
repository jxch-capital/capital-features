package org.jxch.capital.subscriber.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.SubscriberConfigDto;
import org.jxch.capital.http.blog.dto.BlogTitle;
import org.jxch.capital.http.blog.jxch.capital.JxchCapitalBlogApi;
import org.jxch.capital.subscriber.MailSubscriber;
import org.jxch.capital.subscriber.SubscriberParam;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JxchCapitalRandomPriceActionBlogSubscriberImpl implements MailSubscriber {
    private final JxchCapitalBlogApi jxchCapitalBlogApi;

    @Override
    public String mailHtml(SubscriberConfigDto configDto) {
        BlogTitle blogTitle = jxchCapitalBlogApi.randomPriceActionBlog();
        String htmlContent = jxchCapitalBlogApi.blogHtmlContent(blogTitle);
        return "<div><h2><a href=\"" + blogTitle.getUrl() + "\">" + blogTitle.getTitle() + "</a></h2>" + htmlContent + "</div>";
    }

    @Override
    public void addInline(SubscriberConfigDto configDto, MimeMessageHelper helper) {

    }

    @Override
    public void clear(SubscriberConfigDto configDto) {

    }

    @Override
    public SubscriberParam getDefaultParam() {
        return null;
    }

    @Override
    public String name() {
        return "随机价格行为博文";
    }

    @Override
    public int getOrder() {
        return MailSubscriber.super.getOrder() + 1;
    }
}
