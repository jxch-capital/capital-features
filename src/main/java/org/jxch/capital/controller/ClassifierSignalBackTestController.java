package org.jxch.capital.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.*;
import org.jxch.capital.learning.classifier.ClassifierModelService;
import org.jxch.capital.learning.signal.ClassifierSignalBackTestService;
import org.jxch.capital.learning.signal.dto.SignalBackTestClassifierParam;
import org.jxch.capital.learning.signal.filter.SignalFilters;
import org.jxch.capital.server.IndexService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/classifier_model_signal")
public class ClassifierSignalBackTestController {
    private final ClassifierSignalBackTestService classifierSignalBackTestService;
    private final ClassifierModelService classifierModelService;
    private final IndexService indexService;


    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("classifier_model_signal_index");
        modelAndView.addObject("param", new SignalBackTestClassifierParam().setSignalLimitAbs(0));
        modelAndView.addObject("all_local_models", classifierModelService.findModelConfigsHasLocal());
        modelAndView.addObject("all_filters", SignalFilters.allSignalFilterNames());

        return modelAndView;
    }

    @RequestMapping("/signal")
    public ModelAndView signal(@ModelAttribute SignalBackTestClassifierParam param) {
        List<KLineSignal> kLineSignals = classifierSignalBackTestService.backTestByCode(param);
        List<KLineIndices> kLineIndices = indexService.index(kLineSignals.stream().map(KLineSignal::getKLine).toList(), Duration.ofDays(1),
                Collections.singletonList(IndicatorWrapper.builder().name("EMA-20")
                        .indicatorFunc(barSeries -> new EMAIndicator(new ClosePriceIndicator(barSeries), 20)).build()));

        ModelAndView modelAndView = new ModelAndView("classifier_model_signal_index");
        modelAndView.addObject("param", param);
        modelAndView.addObject("all_local_models", classifierModelService.findModelConfigsHasLocal());
        modelAndView.addObject("all_filters", SignalFilters.allSignalFilterNames());

        modelAndView.addObject("kLines", kLineSignals.stream().map(KLineSignal::getKLine).toList());
        modelAndView.addObject("signals", kLineSignals.stream().map(kLineSignal -> EChartsMainIndexDto.builder().date(kLineSignal.getKLine().getDate()).value(kLineSignal.getSignal()).build()).toList());
        modelAndView.addObject("actionSignals", kLineSignals.stream().map(kLineSignal -> EChartsMainIndexDto.builder().date(kLineSignal.getKLine().getDate()).value(kLineSignal.getActionSignal()).build()).toList());
        modelAndView.addObject("ema20", kLineIndices.stream().map(ki -> EChartsMainIndexDto.builder().date(ki.getDate()).value(ki.get("EMA-20")).build()).toList());
        modelAndView.addObject("statistics", new KLineSignalStatistics(kLineSignals, param.getSignalLimitAbs()));

        return modelAndView;
    }

}
