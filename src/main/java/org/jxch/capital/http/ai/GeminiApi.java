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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
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
    public TextAiRes questionTextStream(@NonNull TextAiParam param, Consumer<String> read) {
        GeminiDto geminiParam = param.getParam(GeminiDto.class);
        try (Response response = geminiClient.newCall(getRequest(geminiParam)).execute();
             InputStream is = Objects.requireNonNull(response.body()).byteStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            StringBuilder builder = new StringBuilder();

            boolean end = false;
            int length;
            char[] buffer = new char[240];
            while ((length = br.read(buffer)) != -1) {
                String res = new String(buffer, 0, length);
                builder.append(res);

                if (!end && res.contains("\n") && res.contains("\"text\": \"")) {
                    String text = res.replaceAll("(?s).*\"text\": \"", "").trim();
                    if (text.contains("\"\n")) {
                        text = text.replaceAll("\"(?s).*", "");
                    }
                    read.accept(text);
                } else if (!end && res.contains("\"\n")) {
                    read.accept(res.replaceAll("\"(?s).*", "").trim());
                    end = true;
                } else if (!end) {
                    read.accept(res);
                }
            }

            return JSONObject.parseObject(builder.toString(), GeminiRes.class);
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
