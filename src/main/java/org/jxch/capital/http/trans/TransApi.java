package org.jxch.capital.http.trans;

import jakarta.annotation.Resource;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class TransApi {
    @Resource
    private TransConfig transConfig;
    @Resource
    @Qualifier("okHttpRetrySuccessClient")
    private OkHttpClient okHttpRetrySuccessClient;

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

    public String trans(TransParam param) {
        try (Response response = okHttpRetrySuccessClient.newCall(getRequest(param)).execute()) {
            return Objects.requireNonNull(response.body()).string();
        } catch (Throwable e) {
            if (param.getIgnoredErrorTrans()) {
                return param.getText();
            }

            throw new RuntimeException(e);
        }
    }

}
