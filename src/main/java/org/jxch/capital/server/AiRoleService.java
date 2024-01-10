package org.jxch.capital.server;

import org.jxch.capital.domain.dto.AiRoleDto;

import java.util.List;

public interface AiRoleService {

    List<AiRoleDto> findAll();

    AiRoleDto findById(Long id);

    List<AiRoleDto> findById(List<Long> ids);

    Integer save(List<AiRoleDto> dto);

    void del(List<Long> ids);

}
