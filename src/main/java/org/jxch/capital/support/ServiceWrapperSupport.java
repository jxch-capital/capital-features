package org.jxch.capital.support;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public interface ServiceWrapperSupport extends ServiceNameSupport, ServiceParamSupport<Object> {

    default boolean support(@NotNull ServiceWrapper serviceWrapper) {
        return Objects.equals(serviceWrapper.getName(), name());
    }

    default ServiceWrapper getDefaultServiceWrapper() {
        return ServiceWrapper.builder().name(name()).param(getDefaultParam()).build();
    }

}
