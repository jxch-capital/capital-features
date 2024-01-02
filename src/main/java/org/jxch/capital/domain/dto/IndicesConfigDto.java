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
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class IndicesConfigDto {
    private Long id;
    private String name;
    private String indexName;
    private String indexParamTypes;
    private String indexParams;
    private String remark;

    public IndicesConfigDto paramTypes() {
        if (this.indexName.contains("(")) {
            this.indexParamTypes = this.indexName.substring(this.indexName.indexOf("(") + 1, this.indexName.indexOf(")"));
            this.indexName = this.indexName.substring(0, this.indexName.indexOf("("));
        }
        return this;
    }

    public List<String> getIndexParamTypeList() {
        return Arrays.stream(indexParamTypes.split(",")).toList();
    }

    public List<String> getIndexParamsList() {
        return Arrays.stream(indexParams.split(",")).toList();
    }

}
