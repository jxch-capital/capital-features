package org.jxch.capital.domain.convert;

import org.jxch.capital.domain.dto.WatchConfigDto;
import org.jxch.capital.domain.po.WatchConfig;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WatchConfigMapper {

    WatchConfig toWatchConfig(WatchConfigDto dto);

    List<WatchConfig> toWatchConfig(List<WatchConfigDto> dtoList);

    WatchConfigDto toWatchConfigDto(WatchConfig watchConfig);

    List<WatchConfigDto> toWatchConfigDto(List<WatchConfig> configs);
}
