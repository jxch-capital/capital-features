package org.jxch.capital.domain.po;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity(name = "indices_config")
@NoArgsConstructor
@Table(indexes = {@Index(name = "index_name", columnList = "name"),
        @Index(name = "index_index_name", columnList = "index_name")})
public class IndicesConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = IdGenerators.COMM_SEQ)
    private Long id;
    private String name;
    private String indexName;
    private String indexParamTypes;
    private String indexParams;
    private String remark;
}
