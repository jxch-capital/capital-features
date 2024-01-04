package org.jxch.capital.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KLineAnalyzeStatistics;
import org.jxch.capital.domain.dto.KNNParam;
import org.jxch.capital.domain.dto.KNeighbor;
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
        modelAndView.addObject("knn", KNNs.getAllKNNServicesName());
        modelAndView.addObject("pools", stockPoolService.findAll());
        modelAndView.addObject("all_knn", knnAutoService.allKNNParams());
        modelAndView.addObject("param", knnAutoService.allKNNParams().get(0));
        modelAndView.addObject("indices_com", indicesCombinationService.findAll());
        return modelAndView;
    }

    @RequestMapping("/neighbor")
    public ModelAndView neighbor(@NonNull @ModelAttribute KNNParam knnParam) {
        ModelAndView modelAndView = new ModelAndView("knn_index");
        modelAndView.addObject("knn", KNNs.getAllKNNServicesName());
        List<KNeighbor> neighbors = knnAutoService.search(
                knnParam.getDistanceName(), knnParam.getKNodeParam(), knnParam.getNeighborSize());

        KLineAnalyzeStatistics statistics = kNodeAnalyzeService.statisticsKNN(neighbors, knnParam);

        modelAndView.addObject("neighbors", neighbors);
        modelAndView.addObject("statistics", statistics);
        modelAndView.addObject("pools", stockPoolService.findAll());
        modelAndView.addObject("all_knn", knnAutoService.allKNNParams());
        modelAndView.addObject("param", knnParam);
        modelAndView.addObject("indices_com", indicesCombinationService.findAll());
        return modelAndView;
    }

}
