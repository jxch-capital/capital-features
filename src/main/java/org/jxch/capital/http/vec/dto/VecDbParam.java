package org.jxch.capital.http.vec.dto;

import lombok.*;
import lombok.experimental.Accessors;
import okhttp3.HttpUrl;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class VecDbParam implements VecParam {
    @Builder.Default
    private int num = 8;
    private String question;

    @Override
    public HttpUrl url(@NonNull HttpUrl.Builder builder) {
        return builder.addQueryParameter("question", question)
                .addQueryParameter("num", String.valueOf(num)).build();
    }

}
