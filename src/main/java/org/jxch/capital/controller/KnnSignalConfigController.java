package org.jxch.capital.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KnnSignalConfigDto;
import org.jxch.capital.learning.knn.KNNs;
import org.jxch.capital.server.IndicesCombinationService;
import org.jxch.capital.server.KnnSignalConfigService;
import org.jxch.capital.server.StockPoolService;
import org.jxch.capital.utils.Controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;

@Slf4j
@Controller
@RequestMapping(path = "/knn_signal_config")
@RequiredArgsConstructor
public class KnnSignalConfigController {
    private final KnnSignalConfigService knnSignalConfigService;
    private final IndicesCombinationService indicesCombinationService;
    private final StockPoolService stockPoolService;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("knn_signal_config_index");
        modelAndView.addObject("param", new KnnSignalConfigDto());
        modelAndView.addObject("all_knn", KNNs.getAllKNNServicesName());
        modelAndView.addObject("all_pools", stockPoolService.findAll());
        modelAndView.addObject("all_indices_com", indicesCombinationService.findAll());
        modelAndView.addObject("all_config", knnSignalConfigService.findAll());
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable(value = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("knn_signal_config_edit");
        modelAndView.addObject("param", knnSignalConfigService.findById(id));
        modelAndView.addObject("all_knn", KNNs.getAllKNNServicesName());
        modelAndView.addObject("all_pools", stockPoolService.findAll());
        modelAndView.addObject("all_indices_com", indicesCombinationService.findAll());
        return modelAndView;
    }

    @RequestMapping("/save")
    public String save(@ModelAttribute KnnSignalConfigDto dto) {
        knnSignalConfigService.save(Collections.singletonList(dto));
        return Controllers.redirect("/knn_signal_config/index");
    }

    @GetMapping("/del/{id}")
    public String del(@PathVariable(value = "id") Long id) {
        knnSignalConfigService.del(Collections.singletonList(id));
        return Controllers.redirect("/knn_signal_config/index");
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable(value = "id") Long id) {
        knnSignalConfigService.update(id);
        return Controllers.redirect("/knn_signal_config/index");
    }

    @GetMapping("/update_to_today/{id}")
    public String updateToToday(@PathVariable(value = "id") Long id) {
        knnSignalConfigService.updateToToday(id);
        return Controllers.redirect("/knn_signal_config/index");
    }

}