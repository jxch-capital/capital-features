package org.jxch.capital.watch.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.WatchConfigDto;
import org.jxch.capital.http.finviz.FinvizNewsDto;
import org.jxch.capital.server.FinvizService;
import org.jxch.capital.server.WatchConfigService;
import org.jxch.capital.watch.WatchMailTask;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Collections;
@Deprecated
@Slf4j
@Service
@RequiredArgsConstructor
public class FinvizNewsWatchMailServiceImpl implements WatchMailTask {
    private final FinvizService finvizService;
    private final WatchConfigService watchConfigService;

    @Override
    public boolean support(Long userId) {
        return watchConfigService.userHasWatch(userId, name());
    }

    @Override
    public String htmlBuild(Long userId, String html) {
        StringBuilder htmlBuilder = new StringBuilder(html);
        if (support(userId)) {
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

            for (WatchConfigDto watchConfigDto : watchConfigService.findByUseridAndWatchName(userId, name())) {
                watchConfigService.save(Collections.singletonList(watchConfigDto.setLastWatchTime(Calendar.getInstance().getTime())));
            }
        }
        return htmlBuilder.toString();
    }

    @Override
    public void addInline(Long userId, MimeMessageHelper helper) {

    }

    @Override
    public void clear(Long userId) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Object getDefaultParam() {
        return null;
    }

    @Override
    public String name() {
        return "金融新闻订阅器";
    }

    @Override
    public int getOrder() {
        return WatchMailTask.super.getOrder() + 2;
    }

}
