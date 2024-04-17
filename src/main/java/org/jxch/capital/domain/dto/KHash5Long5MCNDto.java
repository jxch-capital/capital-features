package org.jxch.capital.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class KHash5Long5MCNDto {
    private String code;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private Long hash5;
    private Long hash10;
    private Long hash16;
    private Long hash24;
    private Long hash48;
}
