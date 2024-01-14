package org.jxch.capital.domain.po;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity(name = "ai_role")
@Table(indexes = {@Index(name = "index_name", columnList = "name")})
public class AiRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = IdGenerators.COMM_SEQ)
    private Long id;
    private String name;
    private String text;
    private String remark;
}
