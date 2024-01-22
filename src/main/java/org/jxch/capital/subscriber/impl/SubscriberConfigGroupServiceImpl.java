package org.jxch.capital.subscriber.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.dao.SubscriberConfigGroupRepository;
import org.jxch.capital.domain.convert.SubscriberConfigGroupMapper;
import org.jxch.capital.domain.dto.SubscriberConfigGroupDto;
import org.jxch.capital.subscriber.SubscriberConfigGroupService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriberConfigGroupServiceImpl implements SubscriberConfigGroupService {
    private final SubscriberConfigGroupMapper subscriberConfigGroupMapper;
    private final SubscriberConfigGroupRepository subscriberConfigGroupRepository;

    @Override
    public List<SubscriberConfigGroupDto> findAll() {
        return subscriberConfigGroupMapper.toSubscriberConfigGroupDto(subscriberConfigGroupRepository.findAll());
    }

    @Override
    public SubscriberConfigGroupDto findById(Long id) {
        return subscriberConfigGroupMapper.toSubscriberConfigGroupDto(subscriberConfigGroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("没有这个订阅组ID：" + id)));
    }

    @Override
    public void delById(List<Long> ids) {
        subscriberConfigGroupRepository.deleteAllById(ids);
    }

    @Override
    public Integer save(List<SubscriberConfigGroupDto> dtoList) {
        return subscriberConfigGroupRepository.saveAllAndFlush(subscriberConfigGroupMapper.toSubscriberConfigGroup(dtoList)).size();
    }
}
