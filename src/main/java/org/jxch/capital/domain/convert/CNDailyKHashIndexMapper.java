package org.jxch.capital.domain.convert;

import org.jetbrains.annotations.NotNull;
import org.jxch.capital.domain.dto.CNDailyKHashIndexDto;
import org.jxch.capital.domain.po.CNDailyKHashIndex;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CNDailyKHashIndexMapper {

    default CNDailyKHashIndex toCNDailyKHashIndex(@NotNull CNDailyKHashIndexDto dto) {
        return new CNDailyKHashIndex().setHash(dto.getHash()).setIsFillLength(dto.getIsFillLength()).setLeftVacancies(dto.getLeftVacancies())
                .setId(new CNDailyKHashIndex.CNDailyKHashIndexId().setCode(dto.getCode()).setDate(dto.getDate()));
    }

    default CNDailyKHashIndexDto toCNDailyKHashIndexDto(@NotNull CNDailyKHashIndex po) {
        return CNDailyKHashIndexDto.builder().hash(po.getHash()).code(po.getId().getCode()).date(po.getId().getDate())
                .isFillLength(po.getIsFillLength()).leftVacancies(po.getLeftVacancies()).build();
    }

    default List<CNDailyKHashIndex> toCNDailyKHashIndex(@NotNull List<CNDailyKHashIndexDto> dto) {
        return dto.stream().map(this::toCNDailyKHashIndex).toList();
    }

    default List<CNDailyKHashIndexDto> toCNDailyKHashIndexDto(@NotNull List<CNDailyKHashIndex> po) {
        return po.stream().map(this::toCNDailyKHashIndexDto).toList();
    }

}
