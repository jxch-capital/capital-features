package org.jxch.capital.domain.po;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
@Entity(name = "cn_daily_k_hash_index")
@NoArgsConstructor
@Table(indexes = {@Index(name = "cn_daily_k_hash_index_hash", columnList = "hash")})
public class CNDailyKHashIndex {
    @EmbeddedId
    private CNDailyKHashIndexId id;
    @Column(precision = 50, scale = 0)
    private BigDecimal hash;
    private Boolean isFillLength;
    private Integer leftVacancies;

    @Data
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CNDailyKHashIndexId {
        private String code;
        private Date date;
    }
}
