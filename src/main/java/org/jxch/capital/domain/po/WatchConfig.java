package org.jxch.capital.domain.po;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@Entity(name = "watch_config")
@NoArgsConstructor
@Table(indexes = {@Index(name = "index_watch_config_user_id", columnList = "user_id"),
        @Index(name = "index_watch_config_watch_name", columnList = "watch_name")})
public class WatchConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = IdGenerators.COMM_SEQ)
    @SequenceGenerator(name = IdGenerators.COMM_SEQ, sequenceName = IdGenerators.COMM_SEQ, allocationSize = 1)
    private Long id;
    private Long userId;
    private String watchName;
    @Column(columnDefinition = "TEXT")
    private String param;
    @Column(columnDefinition = "TEXT")
    private String remark;
    private Date lastWatchTime;
}
