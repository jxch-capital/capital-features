package org.jxch.capital.subscriber;

import org.jxch.capital.domain.dto.SubscriberConfigDto;

import java.util.List;

public interface SubscriberConfigService {

    List<SubscriberConfigDto> findAll();

    SubscriberConfigDto findById(Long id);

    void delById(List<Long> ids);

    Integer save(List<SubscriberConfigDto> dtoList);

    List<SubscriberConfigDto> findById(List<Long> ids);

}
