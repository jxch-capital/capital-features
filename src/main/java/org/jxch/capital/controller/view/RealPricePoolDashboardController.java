package org.jxch.capital.controller.view;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.RealPricePoolDashParam;
import org.jxch.capital.domain.dto.StockPoolDto;
import org.jxch.capital.server.RealPricePoolDashboardService;
import org.jxch.capital.server.StockPoolService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
@RequestMapping(path = "/real")
@RequiredArgsConstructor
public class RealPricePoolDashboardController {
    private final RealPricePoolDashboardService realPricePoolDashboardService;
    private final StockPoolService stockPoolService;

    @GetMapping("/index")
    public ModelAndView index() {
        List<StockPoolDto> stockPools = stockPoolService.findAll();
        RealPricePoolDashParam param = RealPricePoolDashParam.builder().offset(120).stockPoolId(stockPools.get(0).getId()).build();
        ModelAndView modelAndView = new ModelAndView("real_index");
        modelAndView.addObject("param", param);
        modelAndView.addObject("pools", stockPools);
        modelAndView.addObject("nodes",
                realPricePoolDashboardService.realDashNodes(param.getStockPoolId(), param.getOffset())
                        .stream().map(kDashNode -> kDashNode.setL(param.getPl(), param.getXl(), param.getYl())).toList());
        return modelAndView;
    }

    @RequestMapping("/search")
    public ModelAndView search(@NonNull @ModelAttribute RealPricePoolDashParam param) {
        if ((param.getYl() + param.getXl() + param.getPl()) >= param.getOffset()) {
            param.setOffset(param.getYl() + param.getXl() + param.getPl() + param.getOffset());
        }

        ModelAndView modelAndView = new ModelAndView("real_index");
        modelAndView.addObject("pools", stockPoolService.findAll());
        modelAndView.addObject("param", param);
        modelAndView.addObject("nodes",
                realPricePoolDashboardService.realDashNodes(param.getStockPoolId(), param.getOffset())
                        .stream().map(kDashNode -> kDashNode.setL(param.getPl(), param.getXl(), param.getYl())).toList());
        return modelAndView;
    }

}
