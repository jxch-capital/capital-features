package org.jxch.capital.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ForkJoinPool;

public class AsyncU {


    @NotNull
    @Contract(" -> new")
    public static ForkJoinPool newForkJoinPool() {
        return newForkJoinPool(Runtime.getRuntime().availableProcessors());
    }

    @NotNull
    @Contract("_ -> new")
    public static ForkJoinPool newForkJoinPool(int num) {
        return new ForkJoinPool(num);
    }

}
