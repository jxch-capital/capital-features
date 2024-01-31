package org.jxch.capital.domain.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.stock.EngineEnum;
import org.jxch.capital.stock.IntervalEnum;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;

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
    private Long parentId;

    @JsonIgnore
    @JSONField(serialize = false)
    public List<String> getPoolStockList() {
        return Arrays.stream(this.poolStocks.split(",")).map(String::trim).toList();
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public IntervalEnum getIntervalEnum() {
        return IntervalEnum.valueOf(this.interval);
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public EngineEnum getEngineEnum() {
        return EngineEnum.pares(this.engine);
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public Boolean isSubPool() {
        return Objects.nonNull(parentId);
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public Long getTopPoolId() {
        return Optional.ofNullable(parentId).orElse(id);
    }

}
