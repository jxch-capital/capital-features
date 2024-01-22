package org.jxch.capital.subscriber;

public interface Subscriber {

    default String name() {
        return getClass().getSimpleName();
    }

    SubscriberParam getDefaultParam();

}
