package org.jxch.capital.domain.convert;

import org.jxch.capital.domain.dto.AiRoleDto;
import org.jxch.capital.domain.po.AiRole;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AiRoleMapper {

    AiRole toAiRole(AiRoleDto dto);

    List<AiRole> toAiRole(List<AiRoleDto> dto);

    AiRoleDto toAiRoleDto(AiRole aiRole);

    List<AiRoleDto> toAiRoleDto(List<AiRole> roles);

}
