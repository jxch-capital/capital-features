package org.jxch.capital.domain.po;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@Entity(name = "knn_signal_history")
@NoArgsConstructor
@Table(indexes = {@Index(name = "index_knn_signal_config_id", columnList = "knn_signal_config_id"),
        @Index(name = "index_code", columnList = "code"),
        @Index(name = "index_date", columnList = "date")})
public class KnnSignalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = IdGenerators.COMM_SEQ)
    private Long id;
    private Long knnSignalConfigId;
    private String code;
    private Date date;
    private Integer signal;
    private Long knnVersion;
}
