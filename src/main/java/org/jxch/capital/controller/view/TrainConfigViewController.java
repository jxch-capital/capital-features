package org.jxch.capital.controller.view;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.TrainConfigDto;
import org.jxch.capital.learning.train.TrainConfigService;
import org.jxch.capital.learning.train.Trains;
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
@RequiredArgsConstructor
@RequestMapping(path = "/train_config_view")
public class TrainConfigViewController {
    private final TrainConfigService trainConfigService;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("train/train_config_index");
        modelAndView.addObject("param", new TrainConfigDto());
        modelAndView.addObject("all_configs", trainConfigService.findAll());
        modelAndView.addObject("all_train_service", Trains.allTrainDataServiceNames());
        return modelAndView;
    }

    @RequestMapping(value = "/edit/{id}")
    public ModelAndView edit(@PathVariable(value = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("train/train_config_edit");
        modelAndView.addObject("param", trainConfigService.findById(id));
        modelAndView.addObject("all_train_service", Trains.allTrainDataServiceNames());
        return modelAndView;
    }

    @RequestMapping(value = "/save")
    public String save(@ModelAttribute TrainConfigDto dto) {
        trainConfigService.save(Collections.singletonList(Trains.setDefaultParams(dto)));
        return Controllers.redirect("/train_config_view/index");
    }

    @RequestMapping(value = "/del/{id}")
    public String del(@PathVariable(value = "id") Long id) {
        trainConfigService.del(Collections.singletonList(id));
        return Controllers.redirect("/train_config_view/index");
    }

}
