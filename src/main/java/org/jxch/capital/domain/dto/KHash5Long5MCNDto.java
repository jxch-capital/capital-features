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
    private String ex;
    private Integer code;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private Long hash5l5s10;
    private Long hash10l10s5;
    private Long hash16l16s3;
    private Long hash24sub1l12s2;
    private Long hash24sub2l12s2;
    private Long hash48sub1l12s1;
    private Long hash48sub2l12s1;
    private Long hash48sub3l12s1;
    private Long hash48sub4l12s1;

    public String getHash48() {
        return String.format("%s%s%s%s", hash48sub1l12s1, hash48sub2l12s1, hash48sub3l12s1, hash48sub4l12s1);
    }

}
