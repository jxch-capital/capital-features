package org.jxch.capital.subscriber.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONObject;
import jakarta.mail.MessagingException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.chart.dto.StockPoolBubbleChartParam;
import org.jxch.capital.chart.dto.StockPoolScatterChartRes;
import org.jxch.capital.chart.impl.StockPoolBubbleChartServiceImpl;
import org.jxch.capital.domain.dto.SubscriberConfigDto;
import org.jxch.capital.subscriber.MailSubscriber;
import org.jxch.capital.subscriber.SubscriberParam;
import org.jxch.capital.subscriber.dto.StockPoolBubbleChartSubscriberParam;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockPoolBubbleChartMailSubscriberImpl implements MailSubscriber {
    private final ThreadLocal<Map<Long, StockPoolScatterChartRes>> configLocal = ThreadLocal.withInitial(ConcurrentHashMap::new);
    private final StockPoolBubbleChartServiceImpl stockPoolChartPngService;

    @Override
    public String mailHtml(@NonNull SubscriberConfigDto configDto) {
        return String.format("<div><img src=\"cid:stock_pool_img_%s\" /></div>", configDto.getId());
    }

    @Override
    public void addInline(@NonNull SubscriberConfigDto configDto, @NonNull MimeMessageHelper helper) {
        try {
            StockPoolBubbleChartParam bubbleChartParam = JSONObject.parseObject(configDto.getParams(), StockPoolBubbleChartSubscriberParam.class).getBubbleChartParam();
            bubbleChartParam.setStart(DateUtil.offset(Calendar.getInstance().getTime(), DateField.DAY_OF_YEAR, -bubbleChartParam.getYl() * 10))
                    .setIdentifier(String.valueOf(configDto.getId()));
            StockPoolScatterChartRes res = stockPoolChartPngService.chart(bubbleChartParam);
            helper.addInline(String.format("stock_pool_img_%s", configDto.getId()), new File(res.getPath()));
            configLocal.get().put(configDto.getId(), res);
        } catch (MessagingException e) {
            log.error("资源加载失败：{}", configDto);
        }
    }

    @Override
    public void clear(@NonNull SubscriberConfigDto configDto) {
        stockPoolChartPngService.clear(configLocal.get().get(configDto.getId()));
        configLocal.get().remove(configDto.getId());
        if (configLocal.get().isEmpty()) {
            configLocal.remove();
        }
    }

    @Override
    public SubscriberParam getDefaultParam() {
        return new StockPoolBubbleChartSubscriberParam();
    }

    @Override
    public String name() {
        return "股票池邮件订阅器";
    }
}
