package org.jxch.capital.domain.po;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@Entity(name = "knn_signal_config")
@NoArgsConstructor
public class KnnSignalConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = IdGenerators.COMM_SEQ)
    private Long id;
    private String name;
    private String distance;
    private Long stockPoolId;
    private Long indicesComId;
    private Boolean isNormalized;
    private Date startDate;
    private Date endDate;
    private Integer size;
    private Integer futureSize;
    private Integer neighborSize;
    private String codes;
    private String stockEngine;
    private Long lastUpdateTimeConsumingSecond;
    private String remark;
    @Version
    private Long version = 0L;
    private Long lastUpdateVersion;
}
