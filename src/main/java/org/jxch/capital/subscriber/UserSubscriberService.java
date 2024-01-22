package org.jxch.capital.subscriber;

import org.jxch.capital.domain.dto.UserSubscriberDto;

import java.util.List;

public interface UserSubscriberService {
    List<UserSubscriberDto> findAll();

    UserSubscriberDto findById(Long id);

    void delById(List<Long> ids);

    Integer save(List<UserSubscriberDto> dtoList);

    List<UserSubscriberDto> findByUserId(Long userId);

    void subscriber(Long id);

}
