package org.jxch.capital.learning.train.config.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.dao.TrainConfigRepository;
import org.jxch.capital.domain.convert.TrainConfigMapper;
import org.jxch.capital.domain.dto.TrainConfigDto;
import org.jxch.capital.learning.train.config.TrainConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainConfigServiceImpl implements TrainConfigService {
    private final TrainConfigRepository trainConfigRepository;
    private final TrainConfigMapper trainConfigMapper;

    @Override
    public List<TrainConfigDto> findAll() {
        return trainConfigMapper.toTrainConfigDto(trainConfigRepository.findAll());
    }

    @Override
    public TrainConfigDto findById(Long id) {
        return trainConfigMapper.toTrainConfigDto(trainConfigRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("没有这个训练配置项：" + id)));
    }

    @Override
    public void del(List<Long> ids) {
        trainConfigRepository.deleteAllById(ids);
    }

    @Override
    public Integer save(List<TrainConfigDto> dtoList) {
        return trainConfigRepository.saveAllAndFlush(trainConfigMapper.toTrainConfig(dtoList)).size();
    }

}
