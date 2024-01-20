package org.jxch.capital.controller.json;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.http.logic.dto.BreathDto;
import org.jxch.capital.http.logic.dto.BreathParam;
import org.jxch.capital.server.BreathService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/market_")
@RequiredArgsConstructor
public class MarketController {
    private final BreathService breathService;

    @ResponseBody
    @RequestMapping("/heatmapTable")
    public List<List<String>> heatmapTable(@RequestBody BreathParam param) {
        BreathDto breath = breathService.getBreath(param);
        return breath.getScoresStrTable();
    }

}
