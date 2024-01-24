package org.jxch.capital.utils;

import lombok.Getter;
import lombok.NonNull;
import org.jxch.capital.support.ServiceNameSupport;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;


@Component
public class AppContextHolder implements ApplicationContextAware {
    @Getter
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static <T> List<T> allService(Class<T> clazz) {
        return context.getBeansOfType(clazz).values().stream().toList();
    }

    public static <T extends ServiceNameSupport> List<String> allServiceName(Class<T> clazz) {
        return allService(clazz).stream().map(ServiceNameSupport::name).toList();
    }

    public static <T extends ServiceNameSupport> T getServiceByName(String name, Class<T> clazz) {
        return allService(clazz).stream().filter(service -> Objects.equals(name, service.name()))
                .findAny().orElseThrow(() -> new IllegalArgumentException(String.format("没有找到这个服务[%s]: %s", clazz.getSimpleName(), name)));
    }

    public <T> List<T> allServiceHold(Class<T> clazz) {
        return context.getBeansOfType(clazz).values().stream().toList();
    }

    public <T extends ServiceNameSupport> List<String> allServiceNameHold(Class<T> clazz) {
        return allService(clazz).stream().map(ServiceNameSupport::name).toList();
    }

    public <T extends ServiceNameSupport> T getServiceByNameHold(String name, Class<T> clazz) {
        return allService(clazz).stream().filter(service -> Objects.equals(name, service.name()))
                .findAny().orElseThrow(() -> new IllegalArgumentException(String.format("没有找到这个服务[%s]: %s", clazz.getSimpleName(), name)));
    }


}
