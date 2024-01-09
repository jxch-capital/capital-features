package org.jxch.capital.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ClassifierPredictParam {
    private String modeName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date start;
    private String code;

    @Builder.Default
    private List<String> filters = null;

    public boolean hasFilter() {
        return Objects.nonNull(filters) && !filters.isEmpty();
    }

}
