package org.jxch.capital.server;

import smile.math.distance.Distance;

import java.util.List;
import java.util.function.Supplier;

public interface DistanceService<T> {

    boolean support(List<T> a, List<T> b, Supplier<Distance<?>> distance);

    double distance(List<T> a, List<T> b, Supplier<Distance<?>> distance);

}
