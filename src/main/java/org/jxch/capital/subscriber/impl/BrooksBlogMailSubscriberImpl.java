package org.jxch.capital.subscriber.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.SubscriberConfigDto;
import org.jxch.capital.server.BrooksService;
import org.jxch.capital.subscriber.MailSubscriber;
import org.jxch.capital.subscriber.SubscriberParam;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrooksBlogMailSubscriberImpl implements MailSubscriber {
    private final BrooksService brooksService;

    @Override
    public String mailHtml(SubscriberConfigDto configDto) {
        return brooksService.newBlogArticleHtmlAndTransToChinese();
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
        return "Brooks最新文章";
    }

    @Override
    public int getOrder() {
        return MailSubscriber.super.getOrder() + 1;
    }

}
