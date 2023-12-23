package org.jxch.capital.yahoo;

import cn.hutool.core.text.csv.CsvUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jxch.capital.yahoo.dto.DownloadStockCsvParam;
import org.jxch.capital.yahoo.dto.HistoryRes;
import org.jxch.capital.yahoo.dto.QuoteParam;
import org.jxch.capital.yahoo.dto.QuoteRes;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class YahooApi {
    private final OkHttpClient yahooClient;
    private final Supplier<HttpUrl.Builder> newYahooQuoteUrlBuilder;
    private final Supplier<HttpUrl.Builder> newYahooDownloadStockCsvUrlBuilder;
    private final Supplier<Request.Builder> newYahooRequestBuilder;


    @SneakyThrows
    public QuoteRes quote(@NonNull QuoteParam param) {
        try (Response response = yahooClient.newCall(param.newRequest(newYahooRequestBuilder, newYahooQuoteUrlBuilder)).execute()) {
            return JSONObject.parseObject(Objects.requireNonNull(response.body()).string(), QuoteRes.class);
        }
    }

    @SneakyThrows
    public List<HistoryRes> downloadStockCsv(@NonNull DownloadStockCsvParam param) {
        try (Response response = yahooClient.newCall(param.newRequest(newYahooRequestBuilder, newYahooDownloadStockCsvUrlBuilder)).execute()) {
            return CsvUtil.getReader().read(Objects.requireNonNull(response.body()).string(), HistoryRes.class);
        }
    }

}
