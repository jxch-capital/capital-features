package org.jxch.capital.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class StockPoolDto {
    private Long id;
    private String poolName;
    private String poolStocks;
    private String engine;
    private String interval;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    public List<String> getPoolStockList() {
        return Arrays.stream(this.poolStocks.split(",")).map(String::trim).toList();
    }
}
