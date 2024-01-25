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
@EqualsAndHashCode(callSuper = true)
public class TensorflowTFModelMetaData extends Model3BaseMetaData {
    private Integer shapex1;
    private Integer shapex2;
    private String inputname;
    private String outputname;
    private Long trainconfigid;
    private String scalername;

    @JsonIgnore
    @JSONField(serialize = false)
    public boolean needScaler() {
        return Objects.nonNull(scalername) && !scalername.isBlank();
    }

}
