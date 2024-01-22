package org.jxch.capital.domain.convert;

import org.jxch.capital.domain.dto.SubscriberConfigGroupDto;
import org.jxch.capital.domain.po.SubscriberConfigGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface SubscriberConfigGroupMapper {

    @Mapping(target = "subscriberConfigIds", expression = "java(idsToString(dto.getSubscriberConfigIds()))")
    SubscriberConfigGroup toSubscriberConfigGroup(SubscriberConfigGroupDto dto);

    List<SubscriberConfigGroup> toSubscriberConfigGroup(List<SubscriberConfigGroupDto> dtoList);

    @Mapping(target = "subscriberConfigIds", expression = "java(stringToIds(group.getSubscriberConfigIds()))")
    SubscriberConfigGroupDto toSubscriberConfigGroupDto(SubscriberConfigGroup group);

    List<SubscriberConfigGroupDto> toSubscriberConfigGroupDto(List<SubscriberConfigGroup> groupList);

    default String idsToString(List<Long> ids) {
        return String.join(",", Optional.ofNullable(ids).orElse(new ArrayList<>()).stream().map(String::valueOf).toList());
    }

    default List<Long> stringToIds(String text) {
        return Arrays.stream(Optional.ofNullable(text).orElse("").trim().split(",")).map(Long::valueOf).toList();
    }

}
