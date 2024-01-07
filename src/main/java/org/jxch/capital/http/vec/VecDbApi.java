package org.jxch.capital.http.vec;

import jakarta.annotation.Resource;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jxch.capital.http.vec.dto.VecDbRes;
import org.jxch.capital.http.vec.dto.VecParam;
import org.jxch.capital.http.vec.dto.VecRes;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Supplier;

@Slf4j
@Service
public class VecDbApi implements VecApi {
    @Resource
    @Qualifier("vecDbUrlBuilder")
    private Supplier<HttpUrl.Builder> urlBuilder;

    @Resource
    @Qualifier("vecDbClient")
    private OkHttpClient vecDbClient;

    @Override
    public VecRes search(@NonNull VecParam param) {
        Request request = new Request.Builder().url(param.url(urlBuilder.get())).build();

        try (Response response = vecDbClient.newCall(request).execute()) {
            return VecDbRes.valueOf(Objects.requireNonNull(response.body()).string());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
