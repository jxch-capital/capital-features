package org.jxch.capital.domain.po;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity(name = "watch_config")
@NoArgsConstructor
@Table(indexes = {@Index(name = "index_user_id", columnList = "user_id"),
        @Index(name = "index_watch_name", columnList = "watch_name")})
public class WatchConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = IdGenerators.COMM_SEQ)
    private Long id;
    private Long userId;
    private String watchName;
    private String param;
    private String remark;
}
