package org.jxch.capital.controller.json;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KDashNode;
import org.jxch.capital.domain.dto.RealPricePoolDashParam;
import org.jxch.capital.domain.dto.StockPoolDto;
import org.jxch.capital.server.RealPricePoolDashboardService;
import org.jxch.capital.server.StockPoolService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/stock_pool_")
@RequiredArgsConstructor
public class StockPoolController {
    private final RealPricePoolDashboardService realPricePoolDashboardService;
    private final StockPoolService stockPoolService;

    @ResponseBody
    @RequestMapping("/all")
    public List<StockPoolDto> all() {
        return stockPoolService.findAll();
    }

    @ResponseBody
    @RequestMapping("/k_dash_by_pool")
    public List<KDashNode> searchKDashByStockPool(@RequestBody @NonNull RealPricePoolDashParam param) {
        return realPricePoolDashboardService.realDashNodes(param.getStockPoolId(), param.getOffset()).stream()
                .map(kDashNode -> kDashNode.setL(param.getPl(), param.getXl(), param.getYl())).toList();
    }

}
