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
        @Index(name = "k_hash_5long_5m_cn_hash5l5s10", columnList = "hash5l5s10"),
        @Index(name = "k_hash_5long_5m_cn_hash10l10s5", columnList = "hash10l10s5"),
        @Index(name = "k_hash_5long_5m_cn_hash16l16s3", columnList = "hash16l16s3"),
        @Index(name = "k_hash_5long_5m_cn_hash24sub1l12s2", columnList = "hash24sub1l12s2"),
        @Index(name = "k_hash_5long_5m_cn_hash24sub2l12s2", columnList = "hash24sub2l12s2"),
        @Index(name = "k_hash_5long_5m_cn_hash48sub1l12s1", columnList = "hash48sub1l12s1"),
        @Index(name = "k_hash_5long_5m_cn_hash48sub2l12s1", columnList = "hash48sub2l12s1"),
        @Index(name = "k_hash_5long_5m_cn_hash48sub3l12s1", columnList = "hash48sub3l12s1"),
        @Index(name = "k_hash_5long_5m_cn_hash48sub4l12s1", columnList = "hash48sub4l12s1"),
})
@Comment("K_Hash, 使用5M-K线进行计算，中国的5M日内K线是48根，Hash的数据结构采用Long类型，便于索引")
public class KHash5Long5MCN {
    @EmbeddedId
    private KHash5MCNId id;
    @Comment("5位hash，每10位选择1位")
    private Long hash5l5s10;
    @Comment("10位hash，每5位选择1位")
    private Long hash10l10s5;
    @Comment("16位hash，每3位选择1位")
    private Long hash16l16s3;
    @Comment("24位hash，每2位选择1位，第1个12位")
    private Long hash24sub1l12s2;
    @Comment("24位hash，每2位选择1位，第2个12位")
    private Long hash24sub2l12s2;
    @Comment("48位hash，全位hash，第1个12位")
    private Long hash48sub1l12s1;
    @Comment("48位hash，全位hash，第2个12位")
    private Long hash48sub2l12s1;
    @Comment("48位hash，全位hash，第3个12位")
    private Long hash48sub3l12s1;
    @Comment("48位hash，全位hash，第4个12位")
    private Long hash48sub4l12s1;
    @Column(length = 2)
    @Comment("2位数的交易所名称：sh代表上交所，sz代表深交所")
    private String ex;

    @Data
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KHash5MCNId {
        @Comment("6位数的股票代码")
        private Integer code;
        private Date date;
    }

}
