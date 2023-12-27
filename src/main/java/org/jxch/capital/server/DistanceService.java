package org.jxch.capital.server;

import java.util.List;

public interface DistanceService<T> {

    double distance(List<T> a, List<T> b);

}
