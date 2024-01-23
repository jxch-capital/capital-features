package org.jxch.capital.watch.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.chart.Colors;
import org.jxch.capital.domain.dto.WatchConfigDto;
import org.jxch.capital.server.BreathService;
import org.jxch.capital.http.logic.dto.BreathDto;
import org.jxch.capital.http.logic.dto.BreathParam;
import org.jxch.capital.server.WatchConfigService;
import org.jxch.capital.watch.WatchMailTask;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Collections;
@Deprecated
@Slf4j
@Service
@RequiredArgsConstructor
public class BreathWatchMailServiceImpl implements WatchMailTask {
    private final WatchConfigService watchConfigService;
    private final BreathService breathService;

    @Override
    public boolean support(Long userId) {
        return watchConfigService.userHasWatch(userId, name());
    }

    @Override
    public String htmlBuild(Long userId, String html) {
        StringBuilder htmlTableBuilder = new StringBuilder(html);
        if (support(userId)) {
            for (WatchConfigDto watchConfigDto : watchConfigService.findByUseridAndWatchName(userId, name())) {
                BreathDto breath = breathService.getBreath(JSONObject.parseObject(watchConfigDto.getParam(), BreathParam.class));
                htmlTableBuilder.append("<table>");
                for (String type : breath.getTypes()) {
                    htmlTableBuilder.append("<tr>");

                    for (BreathDto.Item item : breath.getItems(type)) {
                        Color color = Colors.getColorFromGradientByRTG(item.getScore() - 50, 50);
                        htmlTableBuilder.append("<td style=\"background-color: ").append(Colors.colorTo16(color)).append("; text-align: center;\">");
                        htmlTableBuilder.append(item.getScore());
                        htmlTableBuilder.append("</td>");
                    }

                    htmlTableBuilder.append("<td>").append(type).append("</td>").append("</tr>");
                }

                htmlTableBuilder.append("<tr>");
                for (LocalDate date : breath.getDates()) {
                    String dateStr = DateUtil.format(DateUtil.date(date), "yy\nMM\ndd");
                    htmlTableBuilder.append("<td style=\"text-align: center;\">").append(dateStr).append("</td>");
                }
                htmlTableBuilder.append("</tr>");
                htmlTableBuilder.append("</table></br>");
                watchConfigDto.setLastWatchTime(Calendar.getInstance().getTime());
                watchConfigService.save(Collections.singletonList(watchConfigDto));
            }
        }
        return htmlTableBuilder.toString();
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
        return new BreathParam(90);
    }

    @Override
    public String name() {
        return "市场宽度";
    }

}
