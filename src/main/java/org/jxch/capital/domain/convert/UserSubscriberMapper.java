package org.jxch.capital.domain.convert;

import org.jxch.capital.domain.dto.UserSubscriberDto;
import org.jxch.capital.domain.po.UserSubscriber;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserSubscriberMapper {

    UserSubscriber toUserSubscriber(UserSubscriberDto dto);

    List<UserSubscriber> toUserSubscriber(List<UserSubscriberDto> dtoList);

    UserSubscriberDto toUserSubscriberDto(UserSubscriber subscriber);

    List<UserSubscriberDto> toUserSubscriberDto(List<UserSubscriber> subscribers);

}
