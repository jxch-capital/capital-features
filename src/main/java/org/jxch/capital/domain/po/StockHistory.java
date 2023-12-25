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
@Entity(name = "stock_history")
@NoArgsConstructor
public class StockHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "stock_pool_seq")
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
