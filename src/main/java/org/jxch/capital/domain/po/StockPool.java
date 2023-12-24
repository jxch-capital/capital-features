package org.jxch.capital.domain.po;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class StockPool {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="stock_pool_seq")
    private Long id;
    private String poolName;
    private String poolStocks;
    private String engine;
    private String interval;
    private Date startDate;
    private Date endDate;
}
