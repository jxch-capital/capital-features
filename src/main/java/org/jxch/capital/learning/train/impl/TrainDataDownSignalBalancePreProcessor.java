package org.jxch.capital.learning.train.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.domain.dto.KNodeTrain;
import org.jxch.capital.learning.train.TrainDataSignalBalancePreProcessor;
import org.jxch.capital.learning.train.dto.TrainDataDownSignalBalanceParam;
import org.jxch.capital.support.ServiceWrapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainDataDownSignalBalancePreProcessor  implements TrainDataSignalBalancePreProcessor {
    @Override
    public List<KNodeTrain> kNodeTrainsPostProcess(@NotNull List<KNodeTrain> kNodeTrains, @NotNull ServiceWrapper serviceWrapper) {
        kNodeTrains = kNodeTrains.stream().filter(kNodeTrain -> !kNodeTrain.isReset()).collect(Collectors.toList());
        var param = serviceWrapper.getParamObj(TrainDataDownSignalBalanceParam.class);
        int down = kNodeTrains.stream().mapToInt(KNodeTrain::downSignal).sum();

        if ((double) down / kNodeTrains.size() < 1 - param.getThreshold()) {
            List<KNodeTrain> sorted = kNodeTrains.stream().filter(kNodeTrain -> !kNodeTrain.isDown())
                    .sorted(Comparator.comparing(kNodeTrain -> Math.abs(kNodeTrain.getFuturePercent()))).collect(Collectors.toList());
            while ((double) kNodeTrains.stream().mapToInt(KNodeTrain::downSignal).sum() / kNodeTrains.size() < 1 - param.getThreshold()) {
                KNodeTrain min = sorted.get(0);
                sorted.remove(0);
                kNodeTrains.remove(min);
            }
        } else if ((double) down / kNodeTrains.size() > param.getThreshold()) {
            List<KNodeTrain> sorted = kNodeTrains.stream().filter(KNodeTrain::isDown)
                    .sorted(Comparator.comparing(kNodeTrain -> Math.abs(kNodeTrain.getFuturePercent()))).collect(Collectors.toList());
            while ((double) kNodeTrains.stream().mapToInt(KNodeTrain::downSignal).sum() / kNodeTrains.size() > param.getThreshold()) {
                KNodeTrain min = sorted.get(0);
                sorted.remove(0);
                kNodeTrains.remove(min);
            }
        }

        return kNodeTrains;
    }

    @Override
    public Object getDefaultParam() {
        return new TrainDataDownSignalBalanceParam();
    }

    @Override
    public String name() {
        return "下跌信号平衡器";
    }
}
