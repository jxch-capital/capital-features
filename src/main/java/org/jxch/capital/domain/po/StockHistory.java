package org.jxch.capital.domain.po;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@Entity(name = "stock_history")
@NoArgsConstructor
@Table(indexes = {@Index(name = "index_date", columnList = "date"),
        @Index(name = "index_stock_pool_id", columnList = "stock_pool_id"),
        @Index(name = "index_stock_code", columnList = "stock_code")})
public class StockHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = IdGenerators.STOCK_POOL_SEQ)
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
