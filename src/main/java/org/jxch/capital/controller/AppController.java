package org.jxch.capital.controller;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.*;
import org.jxch.capital.server.IndexService;
import org.jxch.capital.server.StockService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

import java.time.Duration;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/app")
@RequiredArgsConstructor
public class AppController {
    private final StockService stockService;
    private final IndexService indexService;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("app_index");
        modelAndView.addObject("param", HistoryParam.builder().code("QQQ").build());
        return modelAndView;
    }

    @GetMapping("/title")
    public ModelAndView title() {
        return new ModelAndView("app_title");
    }

    @PostMapping(value = "/history")
    public ModelAndView history(@ModelAttribute HistoryParam param) {
        ModelAndView modelAndView = new ModelAndView("app_index");
        modelAndView.addObject("param", param);
        List<KLine> history = stockService.history(param);
        modelAndView.addObject("history", history);
        modelAndView.addObject("volume", history.stream().map(KLine::getVolume).toList());

        List<KLineIndices> kLineIndices = indexService.index(history, Duration.ofDays(1),
                Collections.singletonList(IndicatorWrapper.builder()
                        .name("EMA-20").indicatorFunc(barSeries -> new EMAIndicator(new ClosePriceIndicator(barSeries), 20)).build()));

        modelAndView.addObject("ema20", kLineIndices.stream().map(ki -> EChartsMainIndexDto.builder().date(ki.getDate()).value(ki.get("EMA-20")).build()).toList());

        return modelAndView;
    }

    @GetMapping(value = "/history/{code}")
    public ModelAndView historyByCode(@PathVariable(value = "code") String code) {
        ModelAndView modelAndView = new ModelAndView("app_index");
        HistoryParam param = HistoryParam.builder().code(code)
                .start(DateUtil.offset(Calendar.getInstance().getTime(), DateField.YEAR, -7)).build();
        modelAndView.addObject("param", param);
        List<KLine> history = stockService.history(param);

        List<KLineIndices> kLineIndices = indexService.index(history, Duration.ofDays(1),
                Collections.singletonList(IndicatorWrapper.builder()
                        .name("EMA-20").indicatorFunc(barSeries -> new EMAIndicator(new ClosePriceIndicator(barSeries), 20)).build()));

        modelAndView.addObject("ema20", kLineIndices.stream().map(ki -> EChartsMainIndexDto.builder().date(ki.getDate()).value(ki.get("EMA-20")).build()).toList());
        modelAndView.addObject("history", history);
        return modelAndView;
    }


}
