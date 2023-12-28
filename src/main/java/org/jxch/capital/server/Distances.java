package org.jxch.capital.server;

import org.jxch.capital.utils.AppContextHolder;

import java.util.List;

public class Distances {

    public static List<String> getAllDistanceServices() {
        return AppContextHolder.getContext().getBeansOfType(DistanceService.class).values()
                .stream().map(DistanceService::getName).toList();
    }

    public static DistanceService getDistanceService(String name) {
        return AppContextHolder.getContext().getBeansOfType(DistanceService.class)
                .values().stream()
                .filter(service -> service.getName().equals(name))
                .findAny()
                .orElseThrow();
    }


}
