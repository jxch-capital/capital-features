package org.jxch.capital.domain.po;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Comment;

import java.util.Date;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity(name = "k_hash_5long_5m_cn")
@Table(indexes = {
        @Index(name = "k_hash_5long_5m_cn_hash5", columnList = "hash5"),
        @Index(name = "k_hash_5long_5m_cn_hash10", columnList = "hash10"),
        @Index(name = "k_hash_5long_5m_cn_hash16", columnList = "hash16"),
        @Index(name = "k_hash_5long_5m_cn_hash24", columnList = "hash24"),
        @Index(name = "k_hash_5long_5m_cn_hash48", columnList = "hash48"),
})
@Comment("K_Hash, 使用5M-K线进行计算，中国的5M日内K线是48根，Hash的数据结构采用Long类型，便于索引")
public class KHash5Long5MCN {
    @EmbeddedId
    private KHash5MCNId id;
    private Long hash5;
    private Long hash10;
    private Long hash16;
    private Long hash24;
    private Long hash48;

    @Data
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KHash5MCNId {
        @Column(length = 9)
        private String code;
        private Date date;
    }

}
