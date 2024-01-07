package org.jxch.capital.http.yahoo.dto;

import lombok.NonNull;
import okhttp3.HttpUrl;
import okhttp3.Request;

import java.util.function.Supplier;

public interface UrlParamSupport {
    HttpUrl toUrl(@NonNull HttpUrl.Builder builder);

    default Request newRequest(@NonNull Supplier<Request.Builder> request, @NonNull Supplier<HttpUrl.Builder> url) {
        return request.get().url(toUrl(url.get())).build();
    }
}
