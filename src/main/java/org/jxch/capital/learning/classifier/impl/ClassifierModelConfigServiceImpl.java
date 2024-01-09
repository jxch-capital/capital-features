package org.jxch.capital.learning.classifier.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.dao.ClassifierModelConfigRepository;
import org.jxch.capital.domain.convert.ClassifierModelConfigMapper;
import org.jxch.capital.domain.dto.ClassifierModelConfigDto;
import org.jxch.capital.learning.classifier.ClassifierModelConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassifierModelConfigServiceImpl implements ClassifierModelConfigService {
    private final ClassifierModelConfigRepository classifierModelConfigRepository;
    private final ClassifierModelConfigMapper classifierModelConfigMapper;

    @Override
    public List<ClassifierModelConfigDto> findAll() {
        return classifierModelConfigMapper.toClassifierModelConfigDto(classifierModelConfigRepository.findAll());
    }

    @Override
    public ClassifierModelConfigDto findById(Long id) {
        return classifierModelConfigMapper.toClassifierModelConfigDto(classifierModelConfigRepository
                .findById(id).orElseThrow(() -> new IllegalArgumentException("没有这个模型ID: " + id)));
    }

    @Override
    public List<ClassifierModelConfigDto> findById(List<Long> ids) {
        return classifierModelConfigMapper.toClassifierModelConfigDto(classifierModelConfigRepository.findAllById(ids));
    }

    @Override
    public ClassifierModelConfigDto findByName(String name) {
        return classifierModelConfigMapper.toClassifierModelConfigDto(classifierModelConfigRepository.findByName(name));
    }

    @Override
    public List<ClassifierModelConfigDto> findByName(List<String> names) {
        return classifierModelConfigMapper.toClassifierModelConfigDto(classifierModelConfigRepository.findAllByName(names));
    }

    @Override
    public Integer save(List<ClassifierModelConfigDto> dto) {
        return classifierModelConfigRepository.saveAllAndFlush(classifierModelConfigMapper.toClassifierModelConfig(dto)).size();
    }

    @Override
    public void del(List<Long> ids) {
        classifierModelConfigRepository.deleteAllById(ids);
    }

}
