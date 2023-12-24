package org.jxch.capital.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StockPoolDto {
    private Long id;
    private String poolName;
    private String poolStocks;
    private String engine;
}
