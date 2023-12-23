package org.jxch.capital.yahoo.dto;

import lombok.NonNull;
import okhttp3.HttpUrl;

public interface UrlParamSupport {
    HttpUrl toUrl(@NonNull HttpUrl.Builder builder);
}
