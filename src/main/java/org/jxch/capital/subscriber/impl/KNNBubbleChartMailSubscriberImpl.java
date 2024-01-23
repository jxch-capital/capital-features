package org.jxch.capital.subscriber.impl;

import com.alibaba.fastjson2.JSONObject;
import jakarta.mail.MessagingException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.chart.dto.KNNBubbleChartParam;
import org.jxch.capital.chart.dto.KNNBubbleChartRes;
import org.jxch.capital.chart.impl.KNNBubbleChartServiceImpl;
import org.jxch.capital.domain.dto.SubscriberConfigDto;
import org.jxch.capital.subscriber.MailSubscriber;
import org.jxch.capital.subscriber.SubscriberParam;
import org.jxch.capital.subscriber.dto.KNNBubbleChartSubscriberParam;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class KNNBubbleChartMailSubscriberImpl implements MailSubscriber {
    private final ThreadLocal<Map<Long, KNNBubbleChartRes>> configLocal = ThreadLocal.withInitial(ConcurrentHashMap::new);
    private final KNNBubbleChartServiceImpl knnBubbleChartService;


    @Override
    public String mailHtml(@NonNull SubscriberConfigDto configDto) {
        return String.format("<div><img src=\"cid:knn_bubble_img_%s\" /></div>", configDto.getId());
    }

    @Override
    public void addInline(@NonNull SubscriberConfigDto configDto, @NonNull MimeMessageHelper helper) {
        try {
            KNNBubbleChartParam bubbleChartParam = JSONObject.parseObject(configDto.getParams(), KNNBubbleChartSubscriberParam.class).getKnnBubbleChartParam();
            bubbleChartParam.setIdentifier(String.valueOf(configDto.getId()));
            bubbleChartParam.getKnnParam().getKNodeParam().setEnd(Calendar.getInstance().getTime());
            bubbleChartParam.setTimestamp(Calendar.getInstance().getTime().getTime());
            KNNBubbleChartRes res = knnBubbleChartService.chart(bubbleChartParam);
            helper.addInline(String.format("knn_bubble_img_%s", configDto.getId()), new File(res.getPath()));
            configLocal.get().put(configDto.getId(), res);
        } catch (MessagingException e) {
            log.error("资源加载失败：{}", configDto);
        }
    }

    @Override
    public void clear(@NonNull SubscriberConfigDto configDto) {
        knnBubbleChartService.clear(configLocal.get().get(configDto.getId()));
        configLocal.get().remove(configDto.getId());
        if (configLocal.get().isEmpty()) {
            configLocal.remove();
        }
    }

    @Override
    public SubscriberParam getDefaultParam() {
        return new KNNBubbleChartSubscriberParam();
    }

    @Override
    public String name() {
        return "KNN气泡图";
    }

}
