package org.jxch.capital.domain.convert;

import org.jxch.capital.domain.dto.KnnSignalConfigDto;
import org.jxch.capital.domain.po.KnnSignalConfig;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface KnnSignalConfigMapper {
    KnnSignalConfigDto toKnnSignalConfigDto(KnnSignalConfig config);

    List<KnnSignalConfigDto> toKnnSignalConfigDto(List<KnnSignalConfig> configs);

    KnnSignalConfig toKnnSignalConfig(KnnSignalConfigDto dto);

    List<KnnSignalConfig> toKnnSignalConfig(List<KnnSignalConfigDto> dto);
}
