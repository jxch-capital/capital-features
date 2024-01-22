package org.jxch.capital.subscriber;

import org.springframework.core.Ordered;

public interface Subscriber extends Ordered {

    default String name() {
        return getClass().getSimpleName();
    }

    SubscriberParam getDefaultParam();

    @Override
    default int getOrder() {
        return 0;
    }
}
