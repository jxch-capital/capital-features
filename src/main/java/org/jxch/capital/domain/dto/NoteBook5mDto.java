package org.jxch.capital.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class NoteBook5mDto {
    private Long id;
    private String code;
    private LocalDate date;
    private String type;
    private String key;
    private String value;
    private String remark;
    private String k;
}
