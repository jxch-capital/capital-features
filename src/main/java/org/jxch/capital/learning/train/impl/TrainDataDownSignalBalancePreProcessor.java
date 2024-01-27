package org.jxch.capital.learning.train.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.domain.dto.KNodeTrain;
import org.jxch.capital.learning.train.TrainDataSignalBalancePreProcessor;
import org.jxch.capital.learning.train.dto.TrainDataDownSignalBalanceParam;
import org.jxch.capital.support.ServiceWrapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainDataDownSignalBalancePreProcessor  implements TrainDataSignalBalancePreProcessor {
    @Override
    public List<KNodeTrain> kNodeTrainsPostProcess(@NotNull List<KNodeTrain> kNodeTrains, @NotNull ServiceWrapper serviceWrapper) {
        kNodeTrains = kNodeTrains.parallelStream().filter(kNodeTrain -> !kNodeTrain.isReset()).collect(Collectors.toCollection(LinkedList::new));
        var param = serviceWrapper.getParamObj(TrainDataDownSignalBalanceParam.class);
        double threshold = param.getThreshold();

        long downCount = kNodeTrains.parallelStream().filter(KNodeTrain::isDown).count();
        long totalCount = kNodeTrains.size();

        // 根据当前的上信号比例，决定增加上信号还是减少上信号
        if ((double) downCount / totalCount < threshold) {
            // 移除非上信号节点 down / (size - x) = th
            long x = totalCount - Math.round(downCount / threshold); // 计算要移除的非上信号数
            List<KNodeTrain> downList = kNodeTrains.parallelStream().filter(kNodeTrain -> kNodeTrain.isDown()).toList();
            List<KNodeTrain> notdownList = kNodeTrains.parallelStream().filter(kNodeTrain -> !kNodeTrain.isDown())
                    .sorted(Comparator.comparing(KNodeTrain::getFuturePercent).reversed())
                    .limit(totalCount - downList.size() - x)
                    .toList();
            kNodeTrains = new LinkedList<>(downList);
            kNodeTrains.addAll(notdownList);
        } else if ((double) downCount / totalCount > threshold) {
            // 移除上信号节点 (down - x) / (size - x) = th
            int x = (int) Math.floor((downCount - threshold * totalCount) / (1 - threshold)); // 计算要移除的上信号数
            List<KNodeTrain> notdownList = kNodeTrains.parallelStream().filter(kNodeTrain -> !kNodeTrain.isDown()).toList();
            List<KNodeTrain> downList = kNodeTrains.parallelStream().filter(kNodeTrain -> kNodeTrain.isDown())
                    .sorted(Comparator.comparing(KNodeTrain::getFuturePercent).reversed())
                    .limit(totalCount - notdownList.size() - x)
                    .toList();
            kNodeTrains = new LinkedList<>(notdownList);
            kNodeTrains.addAll(downList);
        }

        Collections.shuffle(kNodeTrains);
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
