package org.jxch.capital.subscriber.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.dao.UserSubscriberRepository;
import org.jxch.capital.domain.convert.UserSubscriberMapper;
import org.jxch.capital.domain.dto.UserSubscriberDto;
import org.jxch.capital.subscriber.UserSubscriberService;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserSubscriberServiceImpl implements UserSubscriberService {
    private final UserSubscriberMapper userSubscriberMapper;
    private final UserSubscriberRepository userSubscriberRepository;

    @Override
    public List<UserSubscriberDto> findAll() {
        return userSubscriberMapper.toUserSubscriberDto(userSubscriberRepository.findAll());
    }

    @Override
    public UserSubscriberDto findById(Long id) {
        return userSubscriberMapper.toUserSubscriberDto(userSubscriberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("没有这个ID: " + id)));
    }

    @Override
    public void delById(List<Long> ids) {
        userSubscriberRepository.deleteAllById(ids);
    }

    @Override
    public Integer save(List<UserSubscriberDto> dtoList) {
        return userSubscriberRepository.saveAllAndFlush(userSubscriberMapper.toUserSubscriber(dtoList)).size();
    }

    @Override
    public List<UserSubscriberDto> findByUserId(Long userId) {
        return userSubscriberMapper.toUserSubscriberDto(userSubscriberRepository.findAllByUserId(userId));
    }
}
