package org.jxch.capital.learning.train.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.domain.dto.KNodeTrain;
import org.jxch.capital.learning.train.TrainDataSignalBalancePreProcessor;
import org.jxch.capital.learning.train.dto.TrainDataUpSignalBalanceParam;
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
public class TrainDataUpSignalBalancePreProcessor implements TrainDataSignalBalancePreProcessor {

    @Override
    public List<KNodeTrain> kNodeTrainsPostProcess(@NotNull List<KNodeTrain> kNodeTrains, @NotNull ServiceWrapper serviceWrapper) {
        kNodeTrains = kNodeTrains.parallelStream().filter(kNodeTrain -> !kNodeTrain.isReset()).collect(Collectors.toCollection(LinkedList::new));
        var param = serviceWrapper.getParamObj(TrainDataUpSignalBalanceParam.class);
        double threshold = param.getThreshold();

        long upCount = kNodeTrains.parallelStream().filter(KNodeTrain::isUp).count();
        long totalCount = kNodeTrains.size();

        // 根据当前的上信号比例，决定增加上信号还是减少上信号
        if ((double) upCount / totalCount < threshold) {
            // 移除非上信号节点 up / (size - x) = th
            long x = totalCount - Math.round(upCount / threshold); // 计算要移除的非上信号数
            List<KNodeTrain> upList = kNodeTrains.parallelStream().filter(kNodeTrain -> kNodeTrain.isUp()).toList();
            List<KNodeTrain> notUpList = kNodeTrains.parallelStream().filter(kNodeTrain -> !kNodeTrain.isUp())
                    .sorted(Comparator.comparing(KNodeTrain::getFuturePercent).reversed())
                    .limit(totalCount - upList.size() - x)
                    .toList();
            kNodeTrains = new LinkedList<>(upList);
            kNodeTrains.addAll(notUpList);
        } else if ((double) upCount / totalCount > threshold) {
            // 移除上信号节点 (up - x) / (size - x) = th
            int x = (int) Math.floor((upCount - threshold * totalCount) / (1 - threshold)); // 计算要移除的上信号数
            List<KNodeTrain> notUpList = kNodeTrains.parallelStream().filter(kNodeTrain -> !kNodeTrain.isUp()).toList();
            List<KNodeTrain> upList = kNodeTrains.parallelStream().filter(kNodeTrain -> kNodeTrain.isUp())
                    .sorted(Comparator.comparing(KNodeTrain::getFuturePercent).reversed())
                    .limit(totalCount - notUpList.size() - x)
                    .toList();
            kNodeTrains = new LinkedList<>(notUpList);
            kNodeTrains.addAll(upList);
        }

        Collections.shuffle(kNodeTrains);
        return kNodeTrains;
    }

    @Override
    public Object getDefaultParam() {
        return new TrainDataUpSignalBalanceParam();
    }

    @Override
    public String name() {
        return "上涨信号平衡器";
    }

}
