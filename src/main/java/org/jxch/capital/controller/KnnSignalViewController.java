package org.jxch.capital.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.domain.dto.KLineSignal;
import org.jxch.capital.domain.dto.KnnSignalParam;
import org.jxch.capital.learning.signal.filter.SignalFilters;
import org.jxch.capital.server.IndexService;
import org.jxch.capital.server.KnnSignalConfigService;
import org.jxch.capital.server.KnnSignalService;
import org.jxch.capital.utils.IndicesU;
import org.jxch.capital.utils.KLineSignals;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
@RequestMapping(path = "/knn_signal_view")
@RequiredArgsConstructor
public class KnnSignalViewController {
    private final KnnSignalService knnSignalService;
    private final KnnSignalConfigService knnSignalConfigService;
    private final IndexService indexService;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("knn_signal_view_index");
        modelAndView.addObject("param", new KnnSignalParam());
        modelAndView.addObject("all_config", knnSignalConfigService.findAllUpdated());
        modelAndView.addObject("all_filters", SignalFilters.allSignalFilterNames());
        return modelAndView;
    }

    @RequestMapping("/find")
    public ModelAndView find(@ModelAttribute KnnSignalParam param) {
        List<KLineSignal> kLineSignal = knnSignalService.findKLineSignal(param);
        List<KLine> kLines = KLineSignals.toKLines(kLineSignal);

        ModelAndView modelAndView = new ModelAndView("knn_signal_view_index");
        modelAndView.addObject("param", param);
        modelAndView.addObject("all_config", knnSignalConfigService.findAllUpdated());
        modelAndView.addObject("all_filters", SignalFilters.allSignalFilterNames());

        modelAndView.addObject("kLines", kLines);
        modelAndView.addObject("signals", KLineSignals.toEChartDtoSignals(kLineSignal));
        modelAndView.addObject("actionSignals", KLineSignals.toEChartDtoActionSignals(kLineSignal));
        modelAndView.addObject("statistics", KLineSignals.toKLineSignalStatistics(kLineSignal, param.getSignalLimitAbs()));
        modelAndView.addObject("ema20", IndicesU.emaXEChartsDto(indexService, kLines, 20));

        return modelAndView;
    }

}
