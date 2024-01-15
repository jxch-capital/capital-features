package org.jxch.capital.http.logic;

import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jxch.capital.http.logic.dto.BreathDto;
import org.jxch.capital.http.logic.dto.BreathResDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class BreathApi {
    private final OkHttpClient okHttpClient;
    private final Request.Builder breathRequestBuilder = new Request.Builder().url("https://www.trading-logic.com/index.html").get();

    @SneakyThrows
    @Cacheable(value = "getBreath", unless = "#result == null")
    public BreathDto getBreath() {
        try (Response response = okHttpClient.newCall(breathRequestBuilder.build()).execute()) {
            String html = Objects.requireNonNull(response.body()).string();
            String jsonString = Objects.requireNonNull(Jsoup.parse(html).select("script[data-for=river]").first()).data();
            return JSONObject.parseObject(jsonString, BreathResDto.class).getBreathDto();
        }
    }

}
