package org.jxch.capital.http.yahoo.dto;

import lombok.*;
import okhttp3.HttpUrl;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuoteParam implements UrlParamSupport {
    private List<String> symbols;

    public String getSymbolsParam() {
        return String.join(",", symbols);
    }

    @Override
    public HttpUrl toUrl(@NonNull HttpUrl.Builder builder) {
        return builder.addQueryParameter("symbols", getSymbolsParam()).build();
    }
}
