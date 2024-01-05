package org.jxch.capital.controller;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.*;
import org.jxch.capital.filter.SignalFilters;
import org.jxch.capital.server.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

import java.time.Duration;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(path = "/back_signal")
@RequiredArgsConstructor
public class SignalBackTestController {
    private final StockPoolService stockPoolService;
    private final IndicesCombinationService indicesCombinationService;
    private final KNNSignalBackTestService knnSignalBackTestService;
    private final IndexService indexService;

    @GetMapping("/knn_index")
    public ModelAndView knnIndex() {
        ModelAndView modelAndView = new ModelAndView("back_signal_knn_index");
        modelAndView.addObject("knn_service_names", KNNs.getAllKNNServicesName());
        modelAndView.addObject("param", new KNNSignalBackTestParam().setCode("QQQ")
                .setStart(DateUtil.offset(Calendar.getInstance().getTime(), DateField.YEAR, -1)));
        modelAndView.addObject("pools", stockPoolService.findAll());
        modelAndView.addObject("indices_com", indicesCombinationService.findAll());
        modelAndView.addObject("all_filters", SignalFilters.allSignalFilterNames());
        return modelAndView;
    }

    @RequestMapping("/knn_back")
    public ModelAndView knnBack(@NonNull @ModelAttribute KNNSignalBackTestParam param) {
        ModelAndView modelAndView = new ModelAndView("back_signal_knn_index");

        List<KLineSignal> kLineSignals = knnSignalBackTestService.backTestByCode(param);
        kLineSignals.forEach(kLineSignal -> kLineSignal.actionSignal(param.getSignalLimitAbs()));
        SignalFilters.chain(SignalFilters.getSignalFilterByName(param.getFilters()), kLineSignals);

        List<KLineIndices> kLineIndices = indexService.index(kLineSignals.stream().map(KLineSignal::getKLine).toList(), Duration.ofDays(1),
                Collections.singletonList(IndicatorWrapper.builder().name("EMA-20").indicatorFunc(barSeries -> new EMAIndicator(new ClosePriceIndicator(barSeries), 20)).build()));

        modelAndView.addObject("kLines", kLineSignals.stream().map(KLineSignal::getKLine).toList());
        modelAndView.addObject("signals", kLineSignals.stream().map(kLineSignal -> EChartsMainIndexDto.builder().date(kLineSignal.getKLine().getDate()).value(kLineSignal.getSignal()).build()).toList());
        modelAndView.addObject("actionSignals", kLineSignals.stream().map(kLineSignal -> EChartsMainIndexDto.builder().date(kLineSignal.getKLine().getDate()).value(kLineSignal.getActionSignal()).build()).toList());
        modelAndView.addObject("ema20", kLineIndices.stream().map(ki -> EChartsMainIndexDto.builder().date(ki.getDate()).value(ki.get("EMA-20")).build()).toList());
        modelAndView.addObject("statistics", new KLineSignalStatistics(kLineSignals, param.getSignalLimitAbs()));
        modelAndView.addObject("param", param);
        modelAndView.addObject("pools", stockPoolService.findAll());
        modelAndView.addObject("knn_service_names", KNNs.getAllKNNServicesName());
        modelAndView.addObject("indices_com", indicesCombinationService.findAll());
        modelAndView.addObject("all_filters", SignalFilters.allSignalFilterNames());
        return modelAndView;
    }

}
