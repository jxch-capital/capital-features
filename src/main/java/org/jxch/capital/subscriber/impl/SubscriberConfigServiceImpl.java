package org.jxch.capital.subscriber.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.dao.SubscriberConfigRepository;
import org.jxch.capital.domain.convert.SubscriberConfigMapper;
import org.jxch.capital.domain.dto.SubscriberConfigDto;
import org.jxch.capital.subscriber.SubscriberConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriberConfigServiceImpl implements SubscriberConfigService {
    private final SubscriberConfigRepository subscriberConfigRepository;
    private final SubscriberConfigMapper subscriberConfigMapper;

    @Override
    public List<SubscriberConfigDto> findAll() {
        return subscriberConfigMapper.toSubscriberConfigDto(subscriberConfigRepository.findAll());
    }

    @Override
    public SubscriberConfigDto findById(Long id) {
        return subscriberConfigMapper.toSubscriberConfigDto(subscriberConfigRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("没有这个ID的订阅配置：" + id)));
    }

    @Override
    public void delById(List<Long> ids) {
        subscriberConfigRepository.deleteAllById(ids);
    }

    @Override
    public Integer save(List<SubscriberConfigDto> dtoList) {
        return subscriberConfigRepository.saveAllAndFlush(subscriberConfigMapper.toSubscriberConfig(dtoList)).size();
    }

}
