package org.jxch.capital.learning.signal.filter;

import lombok.NonNull;
import org.jxch.capital.domain.dto.KLineSignal;
import org.jxch.capital.utils.AppContextHolder;

import java.util.List;
import java.util.Objects;

public class SignalFilters {

    public static List<SignalFilter> allSignalFilters() {
        return AppContextHolder.getContext().getBeansOfType(SignalFilter.class).values().stream().toList();
    }

    public static List<String> allSignalFilterNames() {
        return allSignalFilters().stream().map(SignalFilter::name).sorted().toList();
    }

    public static List<SignalFilter> getSignalFilterByName(List<String> filterNames) {
        return AppContextHolder.getContext().getBeansOfType(SignalFilter.class).values().stream()
                .filter(signalFilter -> filterNames.contains(signalFilter.name()))
                .toList();
    }

    public static SignalFilter getSignalFilterByName(String name) {
        return AppContextHolder.getContext().getBeansOfType(SignalFilter.class).values().stream()
                .filter(signalFilter -> Objects.equals(signalFilter.name(), name))
                .findAny().orElseThrow(() -> new IllegalArgumentException("没有这个过滤器: " + name));
    }

    public static List<KLineSignal> chain(@NonNull List<SignalFilter> filters, List<KLineSignal> signals) {
        filters.forEach(filter -> filter.filter(signals, filter.getDefaultParam()));
        return signals;
    }

    public static List<KLineSignal> chainByFilterNames(List<String> filterNames, List<KLineSignal> signals) {
        if (Objects.nonNull(filterNames) && !filterNames.isEmpty()) {
            List<SignalFilter> filters = getSignalFilterByName(filterNames);
            filters.forEach(filter -> filter.filter(signals, filter.getDefaultParam()));
            return signals;
        }
        return signals;
    }

}
