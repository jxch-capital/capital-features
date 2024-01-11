package org.jxch.capital.domain.dto;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class KnnSignalConfigDto {
    private Long id;
    private String name;
    private String distance;
    private Long stockPoolId;
    private Long indicesComId;
    private Boolean isNormalized = true;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate = DateUtil.offset(Calendar.getInstance().getTime(), DateField.YEAR, -3);
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate = Calendar.getInstance().getTime();
    private Integer size = 20;
    private Integer futureSize = 6;
    private Integer neighborSize = 20;
    private String codes;
    private String stockEngine;
    private Long lastUpdateTimeConsumingSecond;
    private String remark;
    private Long version = 0L;
    private Long lastUpdateVersion;

    public List<String> getCodeList() {
        return Arrays.stream(codes.split(",")).toList();
    }

    public boolean hasEngine() {
        return Objects.nonNull(stockEngine);
    }

    public boolean hasIndicesComId() {
        return Objects.nonNull(indicesComId) && indicesComId > 0;
    }

}
