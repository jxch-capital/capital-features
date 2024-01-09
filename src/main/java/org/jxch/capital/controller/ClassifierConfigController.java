package org.jxch.capital.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.ClassifierConfigDto;
import org.jxch.capital.learning.classifier.ClassifierConfigService;
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
@RequestMapping(path = "/classifier_config")
@RequiredArgsConstructor
public class ClassifierConfigController {
    private final ClassifierConfigService classifierConfigService;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("classifier_config_index");
        modelAndView.addObject("param", new ClassifierConfigDto());
        modelAndView.addObject("all_classifier", classifierConfigService.getAllClassifierSupportDto());
        modelAndView.addObject("db_classifier", classifierConfigService.findAll());
        return modelAndView;
    }

    @GetMapping("/del/{id}")
    public String del(@PathVariable(value = "id") Long id) {
        classifierConfigService.del(Collections.singletonList(id));
        return Controllers.redirect("/classifier_config/index");
    }

    @RequestMapping("/save")
    public String save(@ModelAttribute ClassifierConfigDto dto) {
        classifierConfigService.save(Collections.singletonList(dto));
        return Controllers.redirect("/classifier_config/index");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable(value = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("classifier_config_edit");
        modelAndView.addObject("param", classifierConfigService.findById(id));
        modelAndView.addObject("all_classifier", classifierConfigService.getAllClassifierSupportDto());
        return modelAndView;
    }

}
