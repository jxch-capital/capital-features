package org.jxch.capital.support;

import org.jetbrains.annotations.NotNull;
import org.jxch.capital.utils.AppContextHolder;

import java.util.List;

public interface AutoServiceWrapperSupport {

    default List<ServiceWrapperSupport> allService() {
        return AppContextHolder.allService(ServiceWrapperSupport.class);
    }

    default <T extends ServiceWrapperSupport> List<T> allService(@NotNull Class<T> clazz) {
        return AppContextHolder.allService(ServiceWrapperSupport.class).stream()
                .filter(service -> clazz.isAssignableFrom(service.getClass())).map(clazz::cast).toList();
    }

    default List<ServiceWrapper> allServiceWrapper() {
        return allService().stream().map(ServiceWrapperSupport::getDefaultServiceWrapper).toList();
    }

    default <T extends ServiceWrapperSupport> List<ServiceWrapper> allServiceWrapper(@NotNull Class<T> clazz) {
        return allService(clazz).stream().map(ServiceWrapperSupport::getDefaultServiceWrapper).toList();
    }

}
