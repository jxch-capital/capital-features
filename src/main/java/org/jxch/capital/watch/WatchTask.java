package org.jxch.capital.watch;

public interface WatchTask<T, R> {
    R watchTask(T param);
}
