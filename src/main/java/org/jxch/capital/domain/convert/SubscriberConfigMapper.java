package org.jxch.capital.domain.convert;

import org.jxch.capital.domain.dto.SubscriberConfigDto;
import org.jxch.capital.domain.po.SubscriberConfig;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubscriberConfigMapper {

    SubscriberConfig toSubscriberConfig(SubscriberConfigDto dto);

    List<SubscriberConfig> toSubscriberConfig(List<SubscriberConfigDto> dtoList);

    SubscriberConfigDto toSubscriberConfigDto(SubscriberConfig config);

    List<SubscriberConfigDto> toSubscriberConfigDto(List<SubscriberConfig> configs);

}
