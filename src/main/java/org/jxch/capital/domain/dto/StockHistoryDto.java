package org.jxch.capital.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class StockHistoryDto {
    private Long id;
    private Long stockPoolId;
    private String stockCode;
    private Date date;
    private Double open;
    private Double close;
    private Double high;
    private Double low;
    private Long volume;
}
