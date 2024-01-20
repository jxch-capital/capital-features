package org.jxch.capital.http.finviz;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class FinvizNewsDto {
    private String date;
    private String title;
    private String url;

    @JsonIgnore
    @JSONField(serialize = false)
    public boolean isEffective() {
        return Objects.nonNull(date) && Objects.nonNull(title) && Objects.nonNull(url)
                && !date.isBlank() && !title.isBlank() && !url.isBlank();
    }

}
