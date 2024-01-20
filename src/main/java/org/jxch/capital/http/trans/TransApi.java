package org.jxch.capital.http.trans;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
        return new Request.Builder()
                .url(transConfig.getUrl() + "?text=" + param.getText() + "&target=" + param.getTarget())
                .get().build();
    }

    @SneakyThrows
    public String trans(TransParam param) {
        try (Response response = okHttpClient.newCall(getRequest(param)).execute()) {
            return Objects.requireNonNull(response.body()).string();
        }
    }

}
