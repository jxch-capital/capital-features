package org.jxch.capital.server;

import org.jxch.capital.domain.dto.UserConfigDto;

import java.util.List;

public interface UserConfigService {

    List<UserConfigDto> findAll();

    UserConfigDto findById(Long id);

    UserConfigDto findByUsername(String username);

    UserConfigDto findByEmail(String email);

    void del(Long id);

    void del(List<Long> ids);

    Integer save(List<UserConfigDto> userConfigDtoList);

}
