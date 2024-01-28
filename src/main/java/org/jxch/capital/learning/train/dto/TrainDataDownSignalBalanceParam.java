package org.jxch.capital.learning.train.dto;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class TrainDataDownSignalBalanceParam extends TrainDataSignalBalanceParam {

}
