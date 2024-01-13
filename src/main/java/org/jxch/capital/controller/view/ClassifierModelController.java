package org.jxch.capital.controller.view;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.ClassifierModelConfigDto;
import org.jxch.capital.learning.classifier.ClassifierConfigService;
import org.jxch.capital.learning.classifier.ClassifierModelService;
import org.jxch.capital.server.IndicesCombinationService;
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
@RequestMapping(path = "/classifier_model_config")
@RequiredArgsConstructor
public class ClassifierModelController {
    private final ClassifierModelService classifierModelService;
    private final ClassifierConfigService classifierConfigService;
    private final StockPoolService stockPoolService;
    private final IndicesCombinationService indicesCombinationService;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("classifier_model_config_index");
        modelAndView.addObject("param", new ClassifierModelConfigDto());
        modelAndView.addObject("all_model_configs", classifierModelService.findAllModelConfig());
        modelAndView.addObject("all_classifier_configs", classifierConfigService.findAll());
        modelAndView.addObject("all_combination_indices", indicesCombinationService.findAll());
        modelAndView.addObject("all_stock_pool", stockPoolService.findAll());
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable(value = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("classifier_model_config_edit");
        modelAndView.addObject("param", classifierModelService.findModelConfigById(id));
        modelAndView.addObject("all_model_configs", classifierModelService.findAllModelConfig());
        modelAndView.addObject("all_classifier_configs", classifierConfigService.findAll());
        modelAndView.addObject("all_combination_indices", indicesCombinationService.findAll());
        modelAndView.addObject("all_stock_pool", stockPoolService.findAll());
        return modelAndView;
    }

    @RequestMapping("/save")
    public String save(@ModelAttribute ClassifierModelConfigDto dto) {
        classifierModelService.saveModelConfig(Collections.singletonList(dto));
        return Controllers.redirect("/classifier_model_config/index");
    }

    @GetMapping("/del/{id}")
    public String del(@PathVariable(value = "id") Long id) {
        classifierModelService.delModelAnConfig(Collections.singletonList(id));
        return Controllers.redirect("/classifier_model_config/index");
    }

    @GetMapping("/fit/{id}")
    public String fit(@PathVariable(value = "id") Long id) {
        classifierModelService.fit(id);
        return Controllers.redirect("/classifier_model_config/index");
    }

}
