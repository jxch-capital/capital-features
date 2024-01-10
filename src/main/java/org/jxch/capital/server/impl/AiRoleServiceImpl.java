package org.jxch.capital.server.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.dao.AiRoleRepository;
import org.jxch.capital.domain.convert.AiRoleMapper;
import org.jxch.capital.domain.dto.AiRoleDto;
import org.jxch.capital.server.AiRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiRoleServiceImpl implements AiRoleService {
    private final AiRoleRepository aiRoleRepository;
    private final AiRoleMapper aiRoleMapper;

    @Override
    public List<AiRoleDto> findAll() {
        return aiRoleMapper.toAiRoleDto(aiRoleRepository.findAll());
    }

    @Override
    public AiRoleDto findById(Long id) {
        return aiRoleMapper.toAiRoleDto(aiRoleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("没有这个角色：" + id)));
    }

    @Override
    public List<AiRoleDto> findById(List<Long> ids) {
        return aiRoleMapper.toAiRoleDto(aiRoleRepository.findAllById(ids));
    }

    @Override
    public Integer save(List<AiRoleDto> dto) {
        return aiRoleRepository.saveAllAndFlush(aiRoleMapper.toAiRole(dto)).size();
    }

    @Override
    public void del(List<Long> ids) {
        aiRoleRepository.deleteAllById(ids);
    }

}
