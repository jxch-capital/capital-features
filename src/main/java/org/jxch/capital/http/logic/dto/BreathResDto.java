package org.jxch.capital.http.logic.dto;

import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BreathResDto {
    private X x;

    public List<List<String>> getData() {
        return x.getOpts().getSeries().get(0).getData();
    }

    public BreathDto getBreathDto() {
        BreathDto breathDto = new BreathDto();
        getData().forEach(item -> breathDto.add(DateUtil.parseDate(item.get(0)).toLocalDateTime().toLocalDate(),
                item.get(2), Integer.valueOf(item.get(1).trim())));
        return breathDto;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class X {
        private Opts opts;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class Opts {
        private List<Series> series;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class Series {
        private List<List<String>> data;
    }

}
