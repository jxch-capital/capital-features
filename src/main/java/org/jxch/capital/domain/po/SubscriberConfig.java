package org.jxch.capital.domain.po;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity(name = "subscriber_config")
@Table(indexes = {@Index(name = "subscriber_config_name", columnList = "name")})
public class SubscriberConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = IdGenerators.COMM_SEQ)
    @SequenceGenerator(name = IdGenerators.COMM_SEQ, sequenceName = IdGenerators.COMM_SEQ, allocationSize = 1)
    private Long id;
    @Column(unique = true)
    private String name;
    private String service;
    private String params;
    @Column(columnDefinition = "TEXT")
    private String remark;
}
