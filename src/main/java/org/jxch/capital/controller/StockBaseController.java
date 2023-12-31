package org.jxch.capital.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.StockBaseDto;
import org.jxch.capital.server.StockBaseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;

@Slf4j
@Controller
@RequestMapping(path = "/stock_base")
@RequiredArgsConstructor
public class StockBaseController {
    private final StockBaseService stockBaseService;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("stock_base_index");
        modelAndView.addObject("stockBases", stockBaseService.findAll());
        modelAndView.addObject("stockBaseDto", StockBaseDto.builder().build());
        return modelAndView;
    }

    public String redirect() {
        return "redirect:/stock_base/index";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute StockBaseDto stockBaseDto) {
        stockBaseService.save(Collections.singletonList(stockBaseDto));
        return redirect();
    }

    @RequestMapping("/del/{id}")
    public String del(@PathVariable(value = "id") Long id) {
        stockBaseService.del(Collections.singletonList(id));
        return redirect();
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable(value = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("stock_base_edit");
        modelAndView.addObject("stockBase", stockBaseService.findById(id));
        return modelAndView;
    }

}
