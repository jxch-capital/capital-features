package org.jxch.capital.server;

import org.jxch.capital.domain.dto.IndicatorWrapper;
import org.jxch.capital.domain.dto.KNodeParam;
import org.ta4j.core.indicators.CCIIndicator;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.adx.ADXIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

public class KNodeParams {

    public static KNodeParam LorentzianKNN() {
        return KNodeParam.builder()
                .maxLength(20)
                .size(1)
                .intervalEnum(IntervalEnum.DAY_1)
                .build()
                .addIndicator(IndicatorWrapper.builder().name("CCI").indicatorFunc(barSeries -> new CCIIndicator(barSeries, 20)).build())
                .addIndicator(IndicatorWrapper.builder().name("ADX").indicatorFunc(barSeries -> new ADXIndicator(barSeries, 20)).build())
                .addIndicator(IndicatorWrapper.builder().name("RSI-14").indicatorFunc(barSeries -> new RSIIndicator(new ClosePriceIndicator(barSeries), 14)).build())
                .addIndicator(IndicatorWrapper.builder().name("RSI-20").indicatorFunc(barSeries -> new RSIIndicator(new ClosePriceIndicator(barSeries), 20)).build());
    }

}
