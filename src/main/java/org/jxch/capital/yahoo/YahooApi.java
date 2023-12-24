package org.jxch.capital.yahoo;

import cn.hutool.core.text.csv.CsvUtil;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import lombok.NonNull;
import lombok.SneakyThrows;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jxch.capital.yahoo.dto.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

@Component
public class YahooApi {
    @Resource
    private OkHttpClient yahooClient;
    @Resource
    @Qualifier("newYahooRequestBuilder")
    private  Supplier<Request.Builder> newYahooRequestBuilder;
    @Resource
    @Qualifier("newYahooQuoteUrlBuilder")
    private  Supplier<HttpUrl.Builder> newYahooQuoteUrlBuilder;
    @Resource
    @Qualifier("newYahooDownloadStockCsvUrlBuilder")
    private  Supplier<HttpUrl.Builder> newYahooDownloadStockCsvUrlBuilder;
    @Resource
    @Qualifier("newYahooChartUrlBuilder")
    private  Supplier<HttpUrl.Builder> newYahooChartUrlBuilder;


    @SneakyThrows
    public QuoteRes quote(@NonNull QuoteParam param) {
        try (Response response = yahooClient.newCall(param.newRequest(newYahooRequestBuilder, newYahooQuoteUrlBuilder)).execute()) {
            return JSONObject.parseObject(Objects.requireNonNull(response.body()).string(), QuoteRes.class);
        }
    }

    @SneakyThrows
    public List<DownloadStockCsvRes> downloadStockCsv(@NonNull DownloadStockCsvParam param) {
        try (Response response = yahooClient.newCall(param.newRequest(newYahooRequestBuilder, newYahooDownloadStockCsvUrlBuilder)).execute()) {
            return CsvUtil.getReader().read(Objects.requireNonNull(response.body()).string(), DownloadStockCsvRes.class);
        }
    }

    @SneakyThrows
    public ChartRes chart(@NonNull ChartParam param){
        try (Response response = yahooClient.newCall(param.newRequest(newYahooRequestBuilder, newYahooChartUrlBuilder)).execute()){
            return JSONObject.parseObject(Objects.requireNonNull(response.body()).string(), ChartRes.class);
        }
    }

}
