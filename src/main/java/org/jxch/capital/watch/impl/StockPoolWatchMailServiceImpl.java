package org.jxch.capital.watch.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.chart.dto.StockPoolBubbleChartParam;
import org.jxch.capital.chart.dto.StockPoolScatterChartRes;
import org.jxch.capital.chart.impl.StockPoolBubbleChartServiceImpl;
import org.jxch.capital.domain.dto.WatchConfigDto;
import org.jxch.capital.server.WatchConfigService;
import org.jxch.capital.watch.StockPoolWatchMailService;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockPoolWatchMailServiceImpl implements StockPoolWatchMailService {
    private final StockPoolBubbleChartServiceImpl stockPoolChartPngService;
    private final ThreadLocal<Map<Long, Map<Long, StockPoolScatterChartRes>>> resThreadLocal = new ThreadLocal<>();
    private final WatchConfigService watchConfigService;

    @Override
    public boolean support(Long userId) {
        return watchConfigService.userHasWatch(userId, name());
    }

    private void updateWatchTime(long time, @NonNull WatchConfigDto watchConfigDto) {
        StockPoolBubbleChartParam param = (StockPoolBubbleChartParam) JSONObject.parseObject(watchConfigDto.getParam(), getDefaultParam().getClass());
        String jsonString = JSONObject.toJSONString(param.setTimestamp(time), JSONWriter.Feature.WriteMapNullValue, JSONWriter.Feature.WriteNullListAsEmpty);
        watchConfigDto.setParam(jsonString);
        watchConfigDto.setLastWatchTime(DateUtil.date(time));
        watchConfigService.save(Collections.singletonList(watchConfigDto));
    }

    @Override
    public String htmlBuild(Long userId, String html) {
        if (support(userId)) {
            resThreadLocal.set(new HashMap<>());
            resThreadLocal.get().putIfAbsent(userId, new HashMap<>());

            StringBuilder stringBuilder = new StringBuilder();
            for (WatchConfigDto watchConfigDto : watchConfigService.findByUseridAndWatchName(userId, name())) {
                long time = Calendar.getInstance().getTime().getTime();
                resThreadLocal.get().get(userId).putIfAbsent(time, null);
                updateWatchTime(time, watchConfigDto);
                stringBuilder.append(String.format("<div><img src=\"cid:stock_pool_img_%s%s\" /></div>", userId, time));
            }

            return html + stringBuilder;
        } else {
            return html;
        }
    }

    @Override
    @SneakyThrows
    public void addInline(Long userId, @NonNull MimeMessageHelper helper) {
        if (support(userId)) {
            for (WatchConfigDto watchConfigDto : watchConfigService.findByUseridAndWatchName(userId, name())) {
                StockPoolBubbleChartParam param = (StockPoolBubbleChartParam) JSONObject.parseObject(watchConfigDto.getParam(), getDefaultParam().getClass());
                param.setStart(DateUtil.offset(Calendar.getInstance().getTime(), DateField.DAY_OF_YEAR, -param.getYl() * 10))
                        .setIdentifier(String.valueOf(userId));

                StockPoolScatterChartRes res = stockPoolChartPngService.chart(param);
                helper.addInline(String.format("stock_pool_img_%s%s", userId, param.getTimestamp()), new File(res.getPath()));
                resThreadLocal.get().get(userId).put(param.getTimestamp(), res);
            }
        }
    }

    @Override
    public void clear(Long userId) {
        if (resThreadLocal.get().containsKey(userId)) {
            resThreadLocal.get().get(userId).values().forEach(stockPoolChartPngService::clear);
            resThreadLocal.get().remove(userId);
        }
    }

    @Override
    public void clear() {
        resThreadLocal.remove();
    }

    @Override
    public String name() {
        return "股票池邮件订阅器";
    }

}
