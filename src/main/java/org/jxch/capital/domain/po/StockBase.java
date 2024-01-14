package org.jxch.capital.domain.po;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity(name = "stock_base")
@NoArgsConstructor
@Table(indexes = {@Index(name = "index_code", columnList = "code")})
public class StockBase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = IdGenerators.COMM_SEQ)
    private Long id;
    private String code;
    private String name;
}
