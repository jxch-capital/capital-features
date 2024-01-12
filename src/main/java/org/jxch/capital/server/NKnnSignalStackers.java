package org.jxch.capital.server;

import org.jxch.capital.learning.signal.stack.NKnnSignalStackerService;
import org.jxch.capital.utils.AppContextHolder;

import java.util.List;
import java.util.Objects;

public class NKnnSignalStackers {

    public static List<NKnnSignalStackerService> allNKnnSignalStackerService() {
        return AppContextHolder.getContext().getBeansOfType(NKnnSignalStackerService.class).values().stream().toList();
    }

    public static List<String> allNKnnSignalStackerServiceNames() {
        return allNKnnSignalStackerService().stream().map(NKnnSignalStackerService::name).toList();
    }

    public static NKnnSignalStackerService getNKnnSignalStackerServiceByName(String name) {
        return allNKnnSignalStackerService().stream().filter(service -> Objects.equals(service.name(), name))
                .findAny().orElseThrow(() -> new IllegalArgumentException("没有这个KNN信号叠加器：" + name));
    }

}
