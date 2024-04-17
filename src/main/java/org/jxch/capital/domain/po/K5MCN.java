package org.jxch.capital.domain.po;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@Entity(name = "k_5m_cn")
@NoArgsConstructor
public class K5MCN {
    @EmbeddedId
    private K5MCNId id;
    private double open;
    private double high;
    private double low;
    private double close;
    private Integer volume;
    private Integer amount;

    @Data
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    public static class K5MCNId {
        @Column(length = 9)
        private String code;
        private Date time;
    }
}
