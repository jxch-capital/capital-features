package org.jxch.capital.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.HistoryParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
@RequestMapping(path = "/app")
@RequiredArgsConstructor
public class AppController {

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("param", new HistoryParam());
        return modelAndView;
    }

    @PostMapping(value = "/history")
    public ModelAndView history(@ModelAttribute HistoryParam param) {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("param", param);
        return modelAndView;
    }



}
