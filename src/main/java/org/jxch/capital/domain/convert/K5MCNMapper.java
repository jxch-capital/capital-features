package org.jxch.capital.domain.convert;

import cn.hutool.core.bean.BeanUtil;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.domain.dto.K5MCNCsvDto;
import org.jxch.capital.domain.dto.K5MCNDto;
import org.jxch.capital.domain.po.K5MCN;
import org.jxch.capital.influx.point.K5MCNInfluxPoint;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface K5MCNMapper {

    default K5MCN toK5MCN(@NotNull K5MCNDto dto) {
        K5MCN k5MCN = new K5MCN();
        BeanUtil.copyProperties(dto, k5MCN);
        return k5MCN.setId(new K5MCN.K5MCNId(dto.getCode(), dto.getTime()));
    }

    default K5MCNDto toK5MCNDto(@NotNull K5MCN po) {
        K5MCNDto dto = new K5MCNDto();
        BeanUtil.copyProperties(po, dto);
        return dto.setCode(po.getId().getCode()).setTime(po.getId().getTime());
    }

    default List<K5MCN> toK5MCN(@NotNull List<K5MCNDto> dto) {
        return dto.stream().map(this::toK5MCN).toList();
    }

    default List<K5MCNDto> toK5MCNDto(@NotNull List<K5MCN> po) {
        return po.stream().map(this::toK5MCNDto).toList();
    }

    K5MCNDto toK5MCNDtoByCsvDto(K5MCNCsvDto dto);

    default K5MCNDto toK5MCNDtoByCsvDto(K5MCNCsvDto dto, String code) {
        return toK5MCNDtoByCsvDto(dto).setCode(code);
    }

    List<K5MCNDto> toK5MCNDtoByCsvDto(List<K5MCNCsvDto> csvDtoList);

    default List<K5MCNDto> toK5MCNDtoByCsvDto(List<K5MCNCsvDto> csvDtoList, String code) {
        return toK5MCNDtoByCsvDto(csvDtoList).stream().map(dto -> dto.setCode(code)).toList();
    }

    K5MCNInfluxPoint toK5MCNInfluxPoint(K5MCNCsvDto csvDto);

    default K5MCNInfluxPoint toK5MCNInfluxPoint(K5MCNCsvDto csvDto, String code) {
        return toK5MCNInfluxPoint(csvDto).setCode(code);
    }

    List<K5MCNInfluxPoint> toK5MCNInfluxPoint(List<K5MCNCsvDto> csvDtoList);

    default List<K5MCNInfluxPoint> toK5MCNInfluxPoint(List<K5MCNCsvDto> csvDtoList, String code) {
        return toK5MCNInfluxPoint(csvDtoList).stream().map(dto -> dto.setCode(code)).toList();
    }

}
