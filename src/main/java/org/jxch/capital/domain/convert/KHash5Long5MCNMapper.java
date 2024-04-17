package org.jxch.capital.domain.convert;

import org.jxch.capital.domain.dto.KHash5Long5MCNDto;
import org.jxch.capital.domain.po.KHash5Long5MCN;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface KHash5Long5MCNMapper {

    @Mappings({
            @Mapping(target = "id.code", source = "code"),
            @Mapping(target = "id.date", source = "date"),
    })
    KHash5Long5MCN toKHash5Long5MCN(KHash5Long5MCNDto dto);

    @Mappings({
            @Mapping(target = "code", source = "id.code"),
            @Mapping(target = "date", source = "id.date"),
    })
    KHash5Long5MCNDto toKHash5Long5MCNDto(KHash5Long5MCN po);

    List<KHash5Long5MCN> toKHash5Long5MCN(List<KHash5Long5MCNDto> dtoList);

    List<KHash5Long5MCNDto> toKHash5Long5MCNDto(List<KHash5Long5MCN> dtoList);

}
