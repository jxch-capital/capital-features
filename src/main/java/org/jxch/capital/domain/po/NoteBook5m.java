package org.jxch.capital.domain.po;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
@Entity(name = "notebook_5m")
@NoArgsConstructor
@Table(indexes = {@Index(name = "index_code", columnList = "code"),
        @Index(name = "index_date", columnList = "date")})
public class NoteBook5m {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = IdGenerators.COMM_SEQ)
    private Long id;
    private String code;
    private LocalDate date;
    private String type;
    private String key;
    private String value;
    private String remark;
    private String k;
}