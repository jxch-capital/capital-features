package org.jxch.capital.controller;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import org.jxch.capital.yahoo.YahooApi;
import org.jxch.capital.yahoo.dto.DownloadStockCsvParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

@RestController
@RequestMapping(path = "/yahoo")
@RequiredArgsConstructor
public class YahooController {
    private final YahooApi yahooApi;

    @ResponseBody
    @RequestMapping("test")
    public String yahoo() {
        return JSONObject.toJSONString(yahooApi.downloadStockCsv(DownloadStockCsvParam.builder()
                        .code("QQQ")
                        .start(DateUtil.offset(Calendar.getInstance().getTime(), DateField.MONTH, -3))
                .build()));
    }

}
