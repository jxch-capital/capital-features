package org.jxch.capital.subscriber.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.SubscriberConfigDto;
import org.jxch.capital.http.finviz.FinvizNewsDto;
import org.jxch.capital.server.FinvizService;
import org.jxch.capital.subscriber.MailSubscriber;
import org.jxch.capital.subscriber.SubscriberParam;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FinvizNewsMailSubscriberImpl implements MailSubscriber {
    private final FinvizService finvizService;

    @Override
    public String mailHtml(SubscriberConfigDto configDto) {
        StringBuilder htmlBuilder = new StringBuilder();

        htmlBuilder.append("<div style=\"display: flex; font-size: xx-small\">").append("<table>");
        for (FinvizNewsDto dto : finvizService.newsTitleTransToChinese()) {
            htmlBuilder.append("<tr>").append("<td>").append(dto.getDate()).append("</td>");
            htmlBuilder.append("<td>").append("<a href=\"").append(dto.getUrl()).append("\">").append(dto.getTitle()).append("</a>").append("</td>");
            htmlBuilder.append("</tr>");
        }
        htmlBuilder.append("</table><table>");
        for (FinvizNewsDto dto : finvizService.blogsTitleTransToChinese()) {
            htmlBuilder.append("<tr>").append("<td>").append(dto.getDate()).append("</td>");
            htmlBuilder.append("<td>").append("<a href=\"").append(dto.getUrl()).append("\">").append(dto.getTitle()).append("</a>").append("</td>");
            htmlBuilder.append("</tr>");
        }
        htmlBuilder.append("</div>");

        return htmlBuilder.toString();
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
        return "Finviz新闻列表";
    }

    @Override
    public int getOrder() {
        return MailSubscriber.super.getOrder() + 2;
    }
}
