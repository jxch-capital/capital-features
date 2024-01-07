package org.jxch.capital.http.ai;

import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jxch.capital.http.ai.dto.GeminiDto;
import org.jxch.capital.http.ai.dto.GeminiRes;
import org.jxch.capital.http.ai.dto.TextAiParam;
import org.jxch.capital.http.ai.dto.TextAiRes;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

@Slf4j
@Component
public class GeminiApi implements TextAiApi {
    @Resource
    @Qualifier("geminiClient")
    private OkHttpClient geminiClient;
    @Resource
    @Qualifier("geminiRequestBuilder")
    private Supplier<Request.Builder> geminiRequestBuilder;

    @Override
    public TextAiRes questionText(@NonNull TextAiParam param) {
        GeminiDto geminiParam = param.getParam(GeminiDto.class);
        try (Response response = geminiClient.newCall(getRequest(geminiParam)).execute()) {
            return JSONObject.parseObject(Objects.requireNonNull(response.body()).string(), GeminiRes.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TextAiParam getDefaultParam() {
        return GeminiDto.builder().build();
    }

    @Override
    public TextAiParam getParam(@NonNull List<String> texts) {
        TextAiParam param = getDefaultParam();
        texts.forEach(param::addText);
        return param;
    }

    @NonNull
    private Request getRequest(@NonNull GeminiDto geminiParam) {
        RequestBody body = RequestBody.create(geminiParam.toJsonString(), MediaType.parse("application/json"));
        return geminiRequestBuilder.get()
                .method("POST", body)
                .build();
    }

}
