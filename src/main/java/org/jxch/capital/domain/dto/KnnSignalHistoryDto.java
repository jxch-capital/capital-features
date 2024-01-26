package org.jxch.capital.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class KnnSignalHistoryDto {
    private Long id;
    private Long knnSignalConfigId;
    private String code;
    private Date date;
    private Double signal;
    private Long knnVersion;
}
