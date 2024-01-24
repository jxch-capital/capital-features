package org.jxch.capital.controller.json;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.HistoryParam;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.stock.StockService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/stock_")
public class StockHisController {
    private final StockService  stockService;

    @ResponseBody
    @RequestMapping("/history")
    public List<KLine> heatmapTable(@RequestBody HistoryParam param) {
        return stockService.history(param);
    }

}
