package org.jxch.capital.domain.po;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@Entity(name = "train_config")
@Table(indexes = {@Index(name = "train_config_name", columnList = "name")})
public class TrainConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = IdGenerators.COMM_SEQ)
    @SequenceGenerator(name = IdGenerators.COMM_SEQ, sequenceName = IdGenerators.COMM_SEQ, allocationSize = 1)
    private Long id;
    private String name;
    private String service;
    @Column(columnDefinition = "TEXT")
    private String params;
    @Column(columnDefinition = "TEXT")
    private String remark;
}
