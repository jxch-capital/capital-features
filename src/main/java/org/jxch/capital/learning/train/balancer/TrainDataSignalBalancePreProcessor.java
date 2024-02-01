package org.jxch.capital.learning.train.balancer;

import org.jetbrains.annotations.NotNull;
import org.jxch.capital.domain.dto.KNodeTrain;
import org.jxch.capital.support.ServiceWrapper;
import org.jxch.capital.support.ServiceWrapperSupport;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface TrainDataSignalBalancePreProcessor extends ServiceWrapperSupport {

    default List<KNodeTrain> kNodeTrainsPostProcess(List<KNodeTrain> kNodeTrains, ServiceWrapper serviceWrapper) {
        return kNodeTrains;
    }

    default List<KNodeTrain> remove(@NotNull List<KNodeTrain> kNodeTrains, @NotNull Function<KNodeTrain, Boolean> signalFunc) {
        return kNodeTrains.parallelStream().filter(kNodeTrain -> !signalFunc.apply(kNodeTrain)).collect(Collectors.toCollection(LinkedList::new));
    }

    default List<KNodeTrain> threshold(@NotNull List<KNodeTrain> kNodeTrains, @NotNull Function<KNodeTrain, Boolean> signalFunc, double threshold, @NotNull ServiceWrapper serviceWrapper) {
        long upCount = kNodeTrains.parallelStream().filter(signalFunc::apply).count();
        long totalCount = kNodeTrains.size();

        // 根据当前的上信号比例，决定增加上信号还是减少上信号
        if ((double) upCount / totalCount < threshold) {
            // 移除非上信号节点 up / (size - x) = th
            long x = totalCount - Math.round(upCount / threshold); // 计算要移除的非上信号数
            List<KNodeTrain> upList = kNodeTrains.parallelStream().filter(signalFunc::apply).toList();
            List<KNodeTrain> notUpList = kNodeTrains.parallelStream().filter(kNodeTrain -> !signalFunc.apply(kNodeTrain))
                    .sorted(Comparator.comparing(KNodeTrain::getFuturePercent).reversed())
                    .limit(totalCount - upList.size() - x)
                    .toList();
            kNodeTrains = new LinkedList<>(upList);
            kNodeTrains.addAll(notUpList);
        } else if ((double) upCount / totalCount > threshold) {
            // 移除上信号节点 (up - x) / (size - x) = th   val train in
            int x = (int) Math.floor((upCount - threshold * totalCount) / (1 - threshold)); // 计算要移除的上信号数
            List<KNodeTrain> notUpList = kNodeTrains.parallelStream().filter(kNodeTrain -> !signalFunc.apply(kNodeTrain)).toList();
            List<KNodeTrain> upList = kNodeTrains.parallelStream().filter(signalFunc::apply)
                    .sorted(Comparator.comparing(KNodeTrain::getFuturePercent).reversed())
                    .limit(totalCount - notUpList.size() - x)
                    .toList();
            kNodeTrains = new LinkedList<>(notUpList);
            kNodeTrains.addAll(upList);
        }
        return kNodeTrains;
    }

    default List<KNodeTrain> shuffle(List<KNodeTrain> kNodeTrains) {
        Collections.shuffle(kNodeTrains);
        return kNodeTrains;
    }

}
