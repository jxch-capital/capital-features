package org.jxch.capital.controller.view;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.http.logic.dto.BreathParam;
import org.jxch.capital.server.BreathService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/breath")
public class BreathController {
    private final BreathService breathService;

    @GetMapping("/index")
    public ModelAndView index() {
        BreathParam breathParam = BreathParam.builder().length(90).build();
        ModelAndView modelAndView = new ModelAndView("breath_index");
        modelAndView.addObject("param", breathParam);
        modelAndView.addObject("scores_table", breathService.getBreath(breathParam).getScoresStrTable());
        return modelAndView;
    }

    @RequestMapping("/find")
    public ModelAndView find(@ModelAttribute BreathParam breathParam) {
        ModelAndView modelAndView = new ModelAndView("breath_index");
        modelAndView.addObject("param", breathParam);
        modelAndView.addObject("scores_table", breathService.getBreath(breathParam).getScoresStrTable());
        return modelAndView;
    }

}
