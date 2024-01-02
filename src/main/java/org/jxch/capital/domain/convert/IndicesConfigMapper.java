package org.jxch.capital.domain.convert;

import org.jxch.capital.domain.dto.IndicesConfigDto;
import org.jxch.capital.domain.po.IndicesConfig;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IndicesConfigMapper {

    IndicesConfig toIndicesConfig(IndicesConfigDto dto);

    IndicesConfigDto toIndicesConfigDto(IndicesConfig config);

    List<IndicesConfig> toIndicesConfig(List<IndicesConfigDto> dto);

    List<IndicesConfigDto> toIndicesConfigDto(List<IndicesConfig> dto);

}
