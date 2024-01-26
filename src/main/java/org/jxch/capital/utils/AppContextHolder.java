package org.jxch.capital.utils;

import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.support.ServiceNameSupport;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;


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

    public static <T> List<T> allServiceExcept(Class<T> clazz, Class<?> except) {
        return context.getBeansOfType(clazz).values().stream().filter(service -> !Objects.equals(except, service.getClass())).toList();
    }

    public static <T> T findServiceExcept(Class<T> clazz, Class<?> except, @NotNull Function<T, Boolean> condition) {
        return allServiceExcept(clazz, except).stream().filter(condition::apply)
                .findAny().orElseThrow(() -> new IllegalArgumentException("找不到支持的服务"));
    }

    public static <T> T findFristServiceExcept(Class<T> clazz, Class<?> except, @NotNull Function<T, Boolean> condition) {
        return allServiceExcept(clazz, except).stream().filter(condition::apply)
                .findFirst().orElseThrow(() -> new IllegalArgumentException("找不到支持的服务"));
    }

    public static <T extends Ordered> T findOrderedFristServiceExcept(Class<T> clazz, Class<?> except, @NotNull Function<T, Boolean> condition) {
        return allServiceExcept(clazz, except).stream().filter(condition::apply).min(Comparator.comparing(Ordered::getOrder))
                .orElseThrow(() -> new IllegalArgumentException("找不到支持的服务"));
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
