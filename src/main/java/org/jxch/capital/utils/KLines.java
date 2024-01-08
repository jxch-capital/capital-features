package org.jxch.capital.utils;

import lombok.NonNull;
import org.jxch.capital.domain.dto.KLine;

import java.util.List;

public class KLines {

    @NonNull
    public static List<KLine> normalized(@NonNull List<KLine> kLines) {
        List<Double> close = kLines.stream().map(KLine::getClose).toList();
        List<Double> open = kLines.stream().map(KLine::getOpen).toList();
        List<Double> high = kLines.stream().map(KLine::getHigh).toList();
        List<Double> low = kLines.stream().map(KLine::getLow).toList();

        List<Double> closeN = MathU.normalized(close);
        List<Double> openN = MathU.normalized(open);
        List<Double> highN = MathU.normalized(high);
        List<Double> lowN = MathU.normalized(low);

        for (int i = 0; i < kLines.size(); i++) {
            kLines.get(i).setClose(closeN.get(i));
            kLines.get(i).setOpen(openN.get(i));
            kLines.get(i).setHigh(highN.get(i));
            kLines.get(i).setLow(lowN.get(i));
        }

        return kLines;
    }

}
