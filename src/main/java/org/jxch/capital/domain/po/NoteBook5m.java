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
@Table(indexes = {@Index(name = "index_notebook_5m_code", columnList = "code"),
        @Index(name = "index_notebook_5m_date", columnList = "date")})
public class NoteBook5m {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = IdGenerators.COMM_SEQ)
    @SequenceGenerator(name = IdGenerators.COMM_SEQ, sequenceName = IdGenerators.COMM_SEQ, allocationSize = 1)
    private Long id;
    private String code;
    private LocalDate date;
    private String type;
    private String key;
    private String value;
    @Column(columnDefinition = "TEXT")
    private String remark;
    private String k;
}
