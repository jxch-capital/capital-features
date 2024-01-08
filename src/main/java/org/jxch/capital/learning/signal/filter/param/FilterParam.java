package org.jxch.capital.learning.signal.filter.param;

import org.jxch.capital.domain.dto.IndicatorWrapper;

import java.time.Duration;

public interface FilterParam {

    IndicatorWrapper wrapper();

    Duration duration();

    String name();

}
