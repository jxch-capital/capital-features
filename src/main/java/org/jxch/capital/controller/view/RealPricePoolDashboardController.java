package org.jxch.capital.controller.view;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.RealPricePoolDashParam;
import org.jxch.capital.server.RealPricePoolDashboardService;
import org.jxch.capital.server.StockPoolService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping(path = "/real")
@RequiredArgsConstructor
public class RealPricePoolDashboardController {
    private final RealPricePoolDashboardService realPricePoolDashboardService;
    private final StockPoolService stockPoolService;

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("real_index");
        modelAndView.addObject("param", RealPricePoolDashParam.builder().offset(120).stockPoolId(0L).build());
        modelAndView.addObject("pools", stockPoolService.findAll());
        return modelAndView;
    }

    @RequestMapping("/search")
    public ModelAndView search(@ModelAttribute RealPricePoolDashParam param) {
        ModelAndView modelAndView = new ModelAndView("real_index");
        modelAndView.addObject("pools", stockPoolService.findAll());
        modelAndView.addObject("param", param);
        modelAndView.addObject("nodes",
                realPricePoolDashboardService.realDashNodes(param.getStockPoolId(), param.getOffset()));
        return modelAndView;
    }

}
