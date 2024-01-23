package org.jxch.capital.watch;
@Deprecated
public interface WatchTask<T, R> {
    R watchTask(T param);
}
