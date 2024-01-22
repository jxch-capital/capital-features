package org.jxch.capital.subscriber.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.chart.Colors;
import org.jxch.capital.domain.dto.SubscriberConfigDto;
import org.jxch.capital.http.logic.dto.BreathDto;
import org.jxch.capital.server.BreathService;
import org.jxch.capital.subscriber.MailSubscriber;
import org.jxch.capital.subscriber.SubscriberParam;
import org.jxch.capital.subscriber.convert.ParamConvertMapper;
import org.jxch.capital.subscriber.dto.BreathSubscriberParam;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class BreathMailSubscriberImpl implements MailSubscriber {
    private final ParamConvertMapper paramConvertMapper;
    private final BreathService breathService;

    @Override
    public String mailHtml(@NonNull SubscriberConfigDto configDto) {
        StringBuilder htmlBuilder = new StringBuilder();
        BreathSubscriberParam subscriberParam = JSONObject.parseObject(configDto.getParams(), BreathSubscriberParam.class);
        BreathDto breath = breathService.getBreath(paramConvertMapper.toBreathParam(subscriberParam));

        htmlBuilder.append("<table>");
        for (String type : breath.getTypes()) {
            htmlBuilder.append("<tr>");

            for (BreathDto.Item item : breath.getItems(type)) {
                Color color = Colors.getColorFromGradientByRTG(item.getScore() - 50, 50);
                htmlBuilder.append("<td style=\"background-color: ").append(Colors.colorTo16(color)).append("; text-align: center;\">");
                htmlBuilder.append(item.getScore());
                htmlBuilder.append("</td>");
            }

            htmlBuilder.append("<td>").append(type).append("</td>").append("</tr>");
        }

        htmlBuilder.append("<tr>");
        for (LocalDate date : breath.getDates()) {
            String dateStr = DateUtil.format(DateUtil.date(date), "yy\nMM\ndd");
            htmlBuilder.append("<td style=\"text-align: center;\">").append(dateStr).append("</td>");
        }
        htmlBuilder.append("</tr>");
        htmlBuilder.append("</table></br>");

        return htmlBuilder.toString();
    }

    @Override
    public void addInline(MimeMessageHelper helper) {

    }

    @Override
    public void clear() {

    }


    @Override
    public SubscriberParam getDefaultParam() {
        return new BreathSubscriberParam(90);
    }

    @Override
    public String name() {
        return "市场热力图订阅器";
    }

}
