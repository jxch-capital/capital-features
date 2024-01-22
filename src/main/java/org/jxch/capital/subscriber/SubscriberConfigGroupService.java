package org.jxch.capital.subscriber;

import org.jxch.capital.domain.dto.SubscriberConfigDto;
import org.jxch.capital.domain.dto.SubscriberConfigGroupDto;

import java.util.List;
import java.util.Map;

public interface SubscriberConfigGroupService {
    List<SubscriberConfigGroupDto> findAll();

    SubscriberConfigGroupDto findById(Long id);

    void delById(List<Long> ids);

    Integer save(List<SubscriberConfigGroupDto> dtoList);

    List<SubscriberConfigDto> getDBSubscribersByGroupServiceName(String groupServiceName);

    Map<String, List<SubscriberConfigDto>> groupServiceDBSubscriberMap();

    SubscriberGroupService getGroupServiceById(Long id);
}
