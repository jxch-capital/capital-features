package org.jxch.capital.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.HistoryParam;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.server.StockService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/app")
@RequiredArgsConstructor
public class AppController {
    private final StockService stockService;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("app_index");
        modelAndView.addObject("param", new HistoryParam());
        return modelAndView;
    }

    @GetMapping("/title")
    public ModelAndView title() {
        return new ModelAndView("app_title");
    }

    @PostMapping(value = "/history")
    public ModelAndView history(@ModelAttribute HistoryParam param) {
        ModelAndView modelAndView = new ModelAndView("app_index");
        modelAndView.addObject("param", param);
        List<KLine> history = stockService.history(param);
        modelAndView.addObject("history", history);
        modelAndView.addObject("volume", history.stream().map(KLine::getVolume).toList());
        return modelAndView;
    }


}
