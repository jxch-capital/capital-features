package org.jxch.capital.domain.po;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity(name = "indices_combination")
@NoArgsConstructor
@Table(indexes = {@Index(name = "index_name", columnList = "name")})
public class IndicesCombination {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = IdGenerators.COMM_SEQ)
    private Long id;
    private String name;
    private String indicesIds;
    private Integer maxLength;
    private String remark;
}
