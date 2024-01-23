package org.jxch.capital.watch;

import org.jxch.capital.chart.dto.StockPoolBubbleChartParam;
@Deprecated
public interface StockPoolWatchMailService extends WatchMailTask {

    @Override
    default Object getDefaultParam() {
        return new StockPoolBubbleChartParam();
    }

}
