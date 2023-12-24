package org.jxch.capital.controller;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.StockPoolDto;
import org.jxch.capital.server.StockPoolService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.function.Supplier;

@Slf4j
@Controller
@RequestMapping(path = "/stock_pool")
public class StockPoolController {
    @Resource
    private StockPoolService stockPoolService;
    @Resource
    @Qualifier("newStockPoolIndexView")
    private Supplier<ModelAndView> newStockPoolIndexView;


    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = newStockPoolIndexView.get();
        modelAndView.addObject("stockPools", stockPoolService.findAll());
        return modelAndView;
    }

    public String redirect() {
        return "redirect:/stock_pool/index";
    }

    @PostMapping(value = "/save")
    public String save(@ModelAttribute StockPoolDto stockPool) {
        stockPoolService.save(Collections.singletonList(stockPool));
        return redirect();
    }

    @RequestMapping(value = "/del/{id}")
    public String del(@PathVariable(value = "id") Long id) {
        stockPoolService.del(Collections.singletonList(id));
        return redirect();
    }

}
