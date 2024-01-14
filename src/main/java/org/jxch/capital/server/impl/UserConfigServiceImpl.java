package org.jxch.capital.server.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.dao.UserRepository;
import org.jxch.capital.domain.convert.UserConfigMapper;
import org.jxch.capital.domain.dto.UserConfigDto;
import org.jxch.capital.server.UserConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserConfigServiceImpl implements UserConfigService {
    private final UserConfigMapper userConfigMapper;
    private final UserRepository userRepository;


    @Override
    public List<UserConfigDto> findAll() {
        return userConfigMapper.toUserConfigDto(userRepository.findAll());
    }

    @Override
    public UserConfigDto findById(Long id) {
        return userConfigMapper.toUserConfigDto(userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("没有这个用户ID: " + id)));
    }

    @Override
    public UserConfigDto findByUsername(String username) {
        return userConfigMapper.toUserConfigDto(userRepository.findByUsername(username));
    }

    @Override
    public UserConfigDto findByEmail(String email) {
        return userConfigMapper.toUserConfigDto(userRepository.findByEmail(email));
    }

    @Override
    public void del(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void del(List<Long> ids) {
        userRepository.deleteAllById(ids);
    }

    @Override
    public Integer save(List<UserConfigDto> userConfigDtoList) {
        return userRepository.saveAllAndFlush(userConfigMapper.toUserConfig(userConfigDtoList)).size();
    }

}
