package org.jxch.capital.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ClassifierConfigDto {
    private Long id;
    private String name;
    private String classifierName;
    private String classifierParamTypes;
    private String classifierParams;
    private String remark;
    private String classifierFullName;

    public List<String> getClassifierParmaTypesList() {
        return Arrays.stream(this.classifierParamTypes.split(",")).toList();
    }

    public List<String> getClassifierParamsList() {
        return Arrays.stream(this.classifierParams.split(",")).toList();
    }

}
