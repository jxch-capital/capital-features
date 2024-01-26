package org.jxch.capital.learning.model.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TensorflowTFModelMetaData extends Model3BaseMetaData {
    private Integer shapex1;
    private Integer shapex2;
    private String inputname;
    private String outputname;
    private String scalername;
    private String tags = "serve";

    @JsonIgnore
    @JSONField(serialize = false)
    public boolean needScaler() {
        return Objects.nonNull(scalername) && !scalername.isBlank();
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public String[] getTagArr() {
        return this.tags.split(",");
    }

}
