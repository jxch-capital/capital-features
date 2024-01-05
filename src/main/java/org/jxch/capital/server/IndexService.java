package org.jxch.capital.server;

import org.jxch.capital.domain.dto.IndicatorWrapper;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.domain.dto.KLineIndices;

import java.time.Duration;
import java.util.List;

public interface IndexService {

    List<KLineIndices> index(List<KLine> kLines, Duration barDuration, List<IndicatorWrapper> indicators);

    default List<KLineIndices> index(List<KLine> kLines, Duration barDuration, List<IndicatorWrapper> indicators, int maxLength) {
        return index(kLines, barDuration, indicators).subList(maxLength, kLines.size());
    }

    List<KLineIndices> indexAndNormalized(List<KLine> kLines, Duration barDuration, List<IndicatorWrapper> indicators);

    default List<KLineIndices> indexAndNormalized(List<KLine> kLines, Duration barDuration, List<IndicatorWrapper> indicators, int maxLength) {
        return indexAndNormalized(kLines, barDuration, indicators).subList(maxLength, kLines.size());
    }

}
