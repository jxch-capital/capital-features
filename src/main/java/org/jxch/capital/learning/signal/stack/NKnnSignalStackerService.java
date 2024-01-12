package org.jxch.capital.learning.signal.stack;

import lombok.NonNull;
import org.jxch.capital.domain.dto.KLineSignal;
import org.jxch.capital.domain.dto.KLineSignalStackDto;
import org.jxch.capital.domain.dto.KnnSignalConfigDto;
import org.jxch.capital.server.KnnSignalConfigService;
import org.jxch.capital.utils.AppContextHolder;
import org.jxch.capital.utils.CollU;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface NKnnSignalStackerService {

    default void setTrueSignal(@NonNull KLineSignalStackDto sourceDto, @NonNull KLineSignalStackDto newDtoOnlySignal, KnnSignalConfigDto newConfig) {
        if (sourceDto.getMaxFutureSize() < newDtoOnlySignal.getMaxFutureSize()) {
            sourceDto.setTureSignal(newDtoOnlySignal.getTrueSignal());
        }
    }

    default void setActionSignal(@NonNull KLineSignalStackDto sourceDto, @NonNull KLineSignalStackDto newDtoOnlySignal, KnnSignalConfigDto newConfig) {
        sourceDto.setActionSignal(sourceDto.getActionSignal() + newDtoOnlySignal.getActionSignal());
    }

    default List<KLineSignalStackDto> stack(@NonNull Map<Long, List<KLineSignal>> knnSignalMap) {
        CollU.alignmentMapList(knnSignalMap);

        KnnSignalConfigService knnSignalConfigService = AppContextHolder.getContext().getBean(KnnSignalConfigService.class);
        List<KLineSignalStackDto> kLineSignalStacks = new ArrayList<>();

        for (Long id : knnSignalMap.keySet()) {
            KnnSignalConfigDto newConfig = knnSignalConfigService.findById(id);
            List<KLineSignal> kLineSignals = knnSignalMap.get(id);

            List<KLineSignalStackDto> newStack = kLineSignals.stream()
                    .map(kLineSignal -> new KLineSignalStackDto(newConfig, kLineSignal))
                    .toList();

            if (kLineSignalStacks.isEmpty()) {
                kLineSignalStacks.addAll(newStack);
            } else {
                try {
                    for (int i = 0; i < kLineSignalStacks.size(); i++) {
                        KLineSignalStackDto sourceDto = kLineSignalStacks.get(i);
                        KLineSignalStackDto newDto = newStack.get(i);
                        sourceDto.add(newConfig, newDto);

                        if (newDto.isOnlyKnnSignal()) {
                            setActionSignal(sourceDto, newDto, newConfig);
                            setTrueSignal(sourceDto, newDto, newConfig);
                        } else {
                            throw new RuntimeException("不是唯一的KNN信号");
                        }

                        if (sourceDto.getKLineSignal().getKLine().getDate().getTime() != newDto.getKLineSignal().getKLine().getDate().getTime()) {
                            throw new RuntimeException("时间没有对齐，请先确保数据是向后对齐的");
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new RuntimeException("数据时间未对齐");
                }
            }
        }

        return kLineSignalStacks;
    }

    default String name() {
        return getClass().getSimpleName();
    }

}
