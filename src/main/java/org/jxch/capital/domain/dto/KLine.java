package org.jxch.capital.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class KLine {
    private Date date;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Long volume;
}
