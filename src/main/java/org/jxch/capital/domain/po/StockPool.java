package org.jxch.capital.domain.po;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@Entity(name = "stock_pool")
@NoArgsConstructor
public class StockPool {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = IdGenerators.STOCK_POOL_SEQ)
    private Long id;
    private String poolName;
    private String poolStocks;
    private String engine;
    private String interval;
    private Date startDate;
    private Date endDate;
}
