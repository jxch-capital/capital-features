package org.jxch.capital.subscriber;

import java.util.List;

public interface SubscriberGroupService {

    List<Subscriber> supportSubscribers();

    default String name() {
        return getClass().getSimpleName();
    }

}
