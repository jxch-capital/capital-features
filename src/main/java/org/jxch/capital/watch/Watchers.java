package org.jxch.capital.watch;

import org.jxch.capital.utils.AppContextHolder;

import java.util.List;
import java.util.Objects;

public class Watchers {

    public static List<WatchMailTask> allWatchMailTask() {
        return AppContextHolder.getContext().getBeansOfType(WatchMailTask.class).values().stream().toList();
    }

    public static List<String> allWatchMailTaskNames() {
        return allWatchMailTask().stream().map(WatchMailTask::name).toList();
    }

    public static WatchMailTask getWatchMailTaskByName(String name) {
        return allWatchMailTask().stream().filter(watchMailTask -> Objects.equals(watchMailTask.name(), name))
                .findAny().orElseThrow(() -> new IllegalArgumentException("没有这个订阅器：" + name));
    }

}
