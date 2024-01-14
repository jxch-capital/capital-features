package org.jxch.capital.domain.convert;

import org.jxch.capital.domain.dto.UserConfigDto;
import org.jxch.capital.domain.po.UserConfig;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserConfigMapper {
    UserConfig toUserConfig(UserConfigDto dto);

    List<UserConfig> toUserConfig(List<UserConfigDto> dtoList);

    UserConfigDto toUserConfigDto(UserConfig userConfig);

    List<UserConfigDto> toUserConfigDto(List<UserConfig> userConfigs);
}
