package org.jxch.capital.controller;

import com.alibaba.fastjson2.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.HistoryParam;
import org.jxch.capital.yahoo.YahooApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.function.Supplier;

@Slf4j
@RestController
@RequestMapping(path = "/app")
public class AppController {
    @Resource
    private YahooApi yahooApi;
    @Resource
    @Qualifier("newIndexView")
    private Supplier<ModelAndView> newIndexView;

    @GetMapping("/index")
    public ModelAndView index() {
        return newIndexView.get();
    }

    @PostMapping(value = "/history")
    public ModelAndView history(@ModelAttribute HistoryParam param) {
        ModelAndView modelAndView = newIndexView.get();
        log.info(JSON.toJSONString(param));
        modelAndView.addObject("param", param);
        return modelAndView;
    }



}
