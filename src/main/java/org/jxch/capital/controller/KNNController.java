package org.jxch.capital.controller;

import cn.hutool.core.date.DateField;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.*;
import org.jxch.capital.server.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
@RequestMapping(path = "/knn")
@RequiredArgsConstructor
public class KNNController {
    private final KNNAutoService knnAutoService;
    private final KNodeAnalyzeService kNodeAnalyzeService;
    private final StockPoolService stockPoolService;
    private final IndicesCombinationService indicesCombinationService;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("knn_index");
        modelAndView.addObject("knn", KNNs.getAllDistanceServicesName());
        modelAndView.addObject("pools", stockPoolService.findAll());
        modelAndView.addObject("all_knn", knnAutoService.allKNN());
        modelAndView.addObject("param", knnAutoService.allKNN().get(0));
        modelAndView.addObject("indices_com", indicesCombinationService.findAll());
        return modelAndView;
    }

    @RequestMapping("/neighbor")
    public ModelAndView neighbor(@NonNull @ModelAttribute KNNParam knnParam) {
        ModelAndView modelAndView = new ModelAndView("knn_index");
        modelAndView.addObject("knn", KNNs.getAllDistanceServicesName());
        List<KNeighbor> neighbors = knnAutoService.search(
                knnParam.getDistanceName(), knnParam.getKNodeParam(), knnParam.getNeighborSize());

        List<KLineAnalyzes> kLineAnalyzes = neighbors.stream().map(kNeighbor -> {
            KLineAnalyzedParam analyzedParam = KLineAnalyzedParam.builder()
                    .stockPoolId(knnParam.getKNodeParam().getStockPoolId())
                    .stockCode(kNeighbor.getKNode().getCode())
                    .startDate(kNeighbor.getKNode().getKLines().get(0).getDate())
                    .endDate(kNeighbor.getKNode().getKLines().get(kNeighbor.getKNode().getKLines().size() - 1).getDate())
                    .dateField(DateField.DAY_OF_YEAR)
                    .extend(Math.max(knnParam.getKNodeParam().getSize() + knnParam.getKNodeParam().getFutureNum(),
                            Math.max(2 * knnParam.getKNodeParam().getFutureNum(), 20)))
                    .futureNum(knnParam.getKNodeParam().getFutureNum())
                    .build();
            return kNodeAnalyzeService.analyze(analyzedParam);
        }).toList();
        KLineAnalyzeStatistics statistics = kNodeAnalyzeService.statistics(kLineAnalyzes);

        modelAndView.addObject("neighbors", neighbors);
        modelAndView.addObject("statistics", statistics);
        modelAndView.addObject("pools", stockPoolService.findAll());
        modelAndView.addObject("all_knn", knnAutoService.allKNN());
        modelAndView.addObject("param", knnParam);
        modelAndView.addObject("indices_com", indicesCombinationService.findAll());
        return modelAndView;
    }

}
