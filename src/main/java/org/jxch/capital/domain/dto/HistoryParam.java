package org.jxch.capital.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Calendar;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryParam {
    private String code;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start;

    @Builder.Default
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end = Calendar.getInstance().getTime();

    @Builder.Default
    private String interval = "1d";
}
