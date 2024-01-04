package org.jxch.capital.server;

import org.jxch.capital.utils.AppContextHolder;

import java.util.List;

public class KNNs {

    public static List<String> getAllKNNServicesName() {
        return AppContextHolder.getContext().getBeansOfType(KNNService.class).values()
                .stream().map(KNNService::getName).toList();
    }

    public static KNNService getKNNService(String name) {
        return AppContextHolder.getContext().getBeansOfType(KNNService.class)
                .values().stream()
                .filter(service -> service.getName().equals(name))
                .findAny()
                .orElseThrow();
    }


}
