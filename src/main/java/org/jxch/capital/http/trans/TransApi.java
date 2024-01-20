package org.jxch.capital.http.trans;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jxch.capital.exception.TransServiceException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransApi {
    private final TransConfig transConfig;
    private final OkHttpClient okHttpClient;

    @NonNull
    private Request getRequest(@NonNull TransParam param) {
        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse(transConfig.getUrl()))
                .newBuilder()
                .addQueryParameter("text", param.getText())
                .addQueryParameter("target", param.getTarget())
                .build();

        return new Request.Builder()
                .url(httpUrl)
                .get().build();
    }

    @SneakyThrows
    public String trans(TransParam param) {
        int retry = transConfig.getRetry();
        try (Response response = okHttpClient.newCall(getRequest(param)).execute()) {
            String res = Objects.requireNonNull(response.body()).string();
            if (!response.isSuccessful() || res.isBlank()) {
                if (retry < 0) {
                    if (transConfig.isIgnoredErrorTrans()) {
                        return param.getText();
                    } else {
                        throw new TransServiceException("可能翻译服务器繁忙，请稍后重试");
                    }
                } else {
                    return trans(param);
                }
            }
            return res;
        }
    }

}
