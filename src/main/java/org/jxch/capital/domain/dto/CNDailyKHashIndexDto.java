package org.jxch.capital.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class CNDailyKHashIndexDto {
    private String code;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private BigDecimal hash;
    private Boolean isFillLength;
    private Integer leftVacancies;
}
