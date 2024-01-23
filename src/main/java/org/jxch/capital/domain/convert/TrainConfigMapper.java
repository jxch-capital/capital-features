package org.jxch.capital.domain.convert;

import org.jxch.capital.domain.dto.TrainConfigDto;
import org.jxch.capital.domain.po.TrainConfig;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TrainConfigMapper {

    TrainConfigDto toTrainConfigDto(TrainConfig config);

    List<TrainConfigDto> toTrainConfigDto(List<TrainConfig> configs);

    TrainConfig toTrainConfig(TrainConfigDto dto);

    List<TrainConfig> toTrainConfig(List<TrainConfigDto> configs);

}
