package org.jxch.capital.support;

public interface ServiceNameSupport {

    default String name() {
        return getClass().getSimpleName();
    }

}
