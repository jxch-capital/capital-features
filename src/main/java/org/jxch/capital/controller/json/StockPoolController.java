package org.jxch.capital.controller.json;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KDashNode;
import org.jxch.capital.domain.dto.RealPricePoolDashParam;
import org.jxch.capital.domain.dto.StockPoolDto;
import org.jxch.capital.server.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/stock_pool_")
@RequiredArgsConstructor
public class StockPoolController {
    private final RealPricePoolDashboardService realPricePoolDashboardService;
    private final StockPoolService stockPoolService;
    private final IndicesCombinationService indicesCombinationService;
    private final IndexService indexService;
    private final StockHistoryService stockHistoryService;

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

    @ResponseBody
    @RequestMapping("/stock_pool_prices")
    public List<List<List<Double>>> stockPoolPrices(@RequestParam(value = "stockPoolId") Long stockPoolId, @RequestParam(value = "maxLength") Integer maxLength) {
        return stockPoolService.stockPoolPrices(stockPoolId, maxLength);
    }

    @ResponseBody
    @RequestMapping("/stock_pool_prices_by_ic")
    public List<List<List<Double>>> stockPoolPricesByIc(@RequestParam(value = "stockPoolId") Long stockPoolId,
                                                        @RequestParam(value = "icId") Long icId,
                                                        @RequestParam(value = "maxLength") Integer maxLength) {
        return stockPoolService.stockPoolPricesByIc(stockPoolId, icId, maxLength);
    }

}
