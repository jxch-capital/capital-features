package org.jxch.capital.subscriber;

import org.jxch.capital.domain.dto.UserSubscriberDto;

import java.util.List;

public interface SubscriberGroupService {

    void subscribe(UserSubscriberDto dto);

    List<Subscriber> supportSubscribers();

    default String name() {
        return getClass().getSimpleName();
    }

}
