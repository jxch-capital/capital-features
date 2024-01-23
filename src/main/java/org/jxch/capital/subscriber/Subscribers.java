package org.jxch.capital.subscriber;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.domain.dto.SubscriberConfigDto;
import org.jxch.capital.utils.AppContextHolder;

import java.util.List;
import java.util.Objects;

public class Subscribers {

    public static List<Subscriber> allSubscriber() {
        return AppContextHolder.getContext().getBeansOfType(Subscriber.class).values().stream().toList();
    }

    public static List<String> allSubscriberNames() {
        return allSubscriber().stream().map(Subscriber::name).toList();
    }

    public static Subscriber getSubscriber(String name) {
        return allSubscriber().stream().filter(subscriber -> Objects.equals(subscriber.name(), name))
                .findAny().orElseThrow(() -> new IllegalArgumentException("没有这个订阅器：" + name));
    }

    public static <T extends Subscriber> List<T> allSubscribers(Class<T> subscriberType) {
        return AppContextHolder.getContext().getBeansOfType(subscriberType).values().stream().toList();
    }

    public static <T extends Subscriber> List<String> allSubscribersNames(Class<T> subscriberType) {
        return allSubscribers(subscriberType).stream().map(Subscriber::name).toList();
    }

    public static <T extends Subscriber> T getSubscriber(Class<T> subscriberType, String name) {
        return allSubscribers(subscriberType).stream().filter(subscriber -> Objects.equals(subscriber.name(), name))
                .findAny().orElseThrow(() -> new IllegalArgumentException("没有这个[" + subscriberType.getSimpleName() + "]订阅器：" + name));
    }

    public static List<SubscriberGroupService> allSubscriberGroupService() {
        return AppContextHolder.getContext().getBeansOfType(SubscriberGroupService.class).values().stream().toList();
    }

    public static List<String> allSubscriberGroupServiceNames() {
        return allSubscriberGroupService().stream().map(SubscriberGroupService::name).toList();
    }

    public static SubscriberGroupService getSubscriberGroupService(String name) {
        return allSubscriberGroupService().stream().filter(service -> Objects.equals(service.name(), name))
                .findAny().orElseThrow(() -> new IllegalArgumentException("没有这个聚合订阅器：" + name));
    }

    @NotNull
    @Contract("_ -> param1")
    public static SubscriberConfigDto setDefaultParamsIfIsBlank(@NonNull SubscriberConfigDto dto) {
        if (Objects.isNull(dto.getParams()) || dto.getParams().isBlank()) {
            dto.setParams(JSONObject.toJSONString(getSubscriber(dto.getService()).getDefaultParam(),
                    JSONWriter.Feature.WriteMapNullValue, JSONWriter.Feature.WriteNullListAsEmpty));
        }
        return dto;
    }

}
