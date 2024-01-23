package org.jxch.capital.learning.train.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.domain.dto.KNodeTrains;
import org.jxch.capital.learning.train.TrainDataRes;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TrainIndicesDataRes implements TrainDataRes {
    private KNodeTrains kNodeTrains;
}
