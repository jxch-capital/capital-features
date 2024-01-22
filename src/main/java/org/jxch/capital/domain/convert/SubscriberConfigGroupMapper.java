package org.jxch.capital.domain.convert;

import org.jxch.capital.domain.dto.SubscriberConfigGroupDto;
import org.jxch.capital.domain.po.SubscriberConfigGroup;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubscriberConfigGroupMapper {

    SubscriberConfigGroup toSubscriberConfigGroup(SubscriberConfigGroupDto dto);

    List<SubscriberConfigGroup> toSubscriberConfigGroup(List<SubscriberConfigGroupDto> dtoList);

    SubscriberConfigGroupDto toSubscriberConfigGroupDto(SubscriberConfigGroup group);

    List<SubscriberConfigGroupDto> toSubscriberConfigGroupDto(List<SubscriberConfigGroup> groupList);

}
