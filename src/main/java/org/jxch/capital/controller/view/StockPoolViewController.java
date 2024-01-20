package org.jxch.capital.controller.view;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.StockPoolDto;
import org.jxch.capital.server.StockPoolService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;

@Slf4j
@Controller
@RequestMapping(path = "/stock_pool")
@RequiredArgsConstructor
public class StockPoolViewController {
    private final StockPoolService stockPoolService;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("stock_pool_index");
        modelAndView.addObject("stockPool", new StockPoolDto());
        modelAndView.addObject("stockPools", stockPoolService.findAll());
        return modelAndView;
    }

    @RequestMapping(value = "/edit/{id}")
    public ModelAndView edit(@PathVariable(value = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("stock_pool_edit");
        modelAndView.addObject("stockPool", stockPoolService.findById(id));
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

    @RequestMapping(value = "/update/{id}")
    public String update(@PathVariable(value = "id") Long id) {
        stockPoolService.update(Collections.singletonList(id));
        return redirect();
    }

    @RequestMapping(value = "/update_to_now/{id}")
    public String updateToNow(@PathVariable(value = "id") Long id) {
        stockPoolService.updateToNow(Collections.singletonList(id));
        return redirect();
    }

}
