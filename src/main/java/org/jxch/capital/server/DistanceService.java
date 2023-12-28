package org.jxch.capital.server;

import org.jxch.capital.domain.dto.KLine;

import java.util.List;

public interface DistanceService {

    double distance(List<KLine> a, List<KLine> b);

    default String getName() {
        return getClass().getSimpleName();
    }


}
