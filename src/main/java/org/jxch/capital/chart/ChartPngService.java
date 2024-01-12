package org.jxch.capital.chart;

import org.jxch.capital.chart.dto.ChartParam;
import org.jxch.capital.chart.dto.ChartRes;

public interface ChartPngService<T extends ChartParam, R extends ChartRes> {

    R chart(T param);

    default void clear(R res) {

    }

}
