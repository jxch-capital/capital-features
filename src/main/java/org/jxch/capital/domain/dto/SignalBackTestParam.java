package org.jxch.capital.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Calendar;
import java.util.Date;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SignalBackTestParam {
    private String code;
    private Date start;

    @Builder.Default
    private Integer futureNum = 4;
    @Builder.Default
    private Date end = Calendar.getInstance().getTime();
}
