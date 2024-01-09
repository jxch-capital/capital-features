package org.jxch.capital.domain.po;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity(name = "classifier_config")
@NoArgsConstructor
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
