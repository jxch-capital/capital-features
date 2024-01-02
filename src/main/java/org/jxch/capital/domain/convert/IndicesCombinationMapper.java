package org.jxch.capital.domain.convert;

import org.jxch.capital.domain.dto.IndicesCombinationDto;
import org.jxch.capital.domain.po.IndicesCombination;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IndicesCombinationMapper {

    IndicesCombinationDto toIndicesCombinationDto(IndicesCombination indicesCombination);

    IndicesCombination toIndicesCombination(IndicesCombinationDto dto);

    List<IndicesCombinationDto> toIndicesCombinationDto(List<IndicesCombination> indicesCombination);

    List<IndicesCombination> toIndicesCombination(List<IndicesCombinationDto> dto);

}
