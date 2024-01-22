package org.jxch.capital.subscriber;

import org.jxch.capital.domain.dto.SubscriberConfigGroupDto;

import java.util.List;

public interface SubscriberConfigGroupService {
    List<SubscriberConfigGroupDto> findAll();

    SubscriberConfigGroupDto findById(Long id);

    void delById(List<Long> ids);

    Integer save(List<SubscriberConfigGroupDto> dtoList);
}
