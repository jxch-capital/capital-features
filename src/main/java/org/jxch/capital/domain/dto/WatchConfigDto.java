package org.jxch.capital.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.Objects;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class WatchConfigDto {
    private Long id;
    private Long userId;
    private String watchName;
    private String param;
    private String remark;
    private Date lastWatchTime;

    public boolean hasParam() {
        return Objects.nonNull(param) && !param.isBlank();
    }

}
