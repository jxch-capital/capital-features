package org.jxch.capital.http.vec.dto;

import okhttp3.HttpUrl;

public interface VecParam {

    HttpUrl url(HttpUrl.Builder builder);

}
