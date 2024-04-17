package org.jxch.capital.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class K5MCNCsvDto {
    @DateTimeFormat(pattern = "yyyyMMddHHmmssSSS")
    private Date time;
    private double open;
    private double high;
    private double low;
    private double close;
    private Integer volume;
    private Integer amount;
}
