package org.jxch.capital.domain.po;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity(name = "classifier_config")
@NoArgsConstructor
@Table(indexes = {@Index(name = "index_name", columnList = "name"),
        @Index(name = "index_classifier_name", columnList = "classifier_name")})
public class ClassifierConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = IdGenerators.COMM_SEQ)
    private Long id;
    private String name;
    private String classifierName;
    private String classifierParamTypes;
    private String classifierParams;
    private String remark;
}
