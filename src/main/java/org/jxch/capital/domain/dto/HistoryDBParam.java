package org.jxch.capital.domain.dto;

import lombok.*;

@Data
@Builder(builderMethodName = "historyDBParamBuilder")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class HistoryDBParam extends HistoryParam {
    private Long stockPoolId;
}
