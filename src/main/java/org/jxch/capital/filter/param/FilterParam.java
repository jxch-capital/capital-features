package org.jxch.capital.filter.param;

import org.jxch.capital.domain.dto.IndicatorWrapper;

import java.time.Duration;

public interface FilterParam {

    IndicatorWrapper wrapper();

    Duration duration();

    String name();

}
