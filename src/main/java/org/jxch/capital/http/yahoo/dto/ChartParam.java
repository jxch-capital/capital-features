package org.jxch.capital.http.yahoo.dto;

import lombok.*;
import okhttp3.HttpUrl;

import java.util.Calendar;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChartParam implements UrlParamSupport {
    private String code;
    private Date start;
    @Builder.Default
    private Date end = Calendar.getInstance().getTime();
    @Builder.Default
    private String interval = "1d";

    public String getPeriod1Param() {
        return String.valueOf(this.start.getTime() / 1000);
    }

    public String getPeriod2Param() {
        return String.valueOf(this.end.getTime() / 1000);
    }

    public String getIntervalParam() {
        return this.interval;
    }

    @Override
    public HttpUrl toUrl(HttpUrl.@NonNull Builder builder) {
        return builder
                .addPathSegments(getCode())
                .addQueryParameter("period1", getPeriod1Param())
                .addQueryParameter("period2", getPeriod2Param())
                .addQueryParameter("interval", getIntervalParam())
                .build();
    }
}
