package org.jxch.capital.domain.po;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity(name = "classifier_model_config")
@NoArgsConstructor
@Table(indexes = {@Index(name = "index_name", columnList = "name")})
public class ClassifierModelConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = IdGenerators.COMM_SEQ)
    private Long id;
    private String name;
    private Long classifierId;
    private Long stockPoolId;
    private Long indicesComId;
    private Integer size;
    private Integer futureNum;
    private String remark;
}
