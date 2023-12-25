package org.jxch.capital.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class KLine {
    private Date date;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Long volume;
}
