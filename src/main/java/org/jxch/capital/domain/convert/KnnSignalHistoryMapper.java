package org.jxch.capital.domain.convert;

import lombok.NonNull;
import org.jxch.capital.domain.dto.KLineSignal;
import org.jxch.capital.domain.dto.KnnSignalConfigDto;
import org.jxch.capital.domain.dto.KnnSignalHistoryDto;
import org.jxch.capital.domain.po.KnnSignalHistory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface KnnSignalHistoryMapper {
    KnnSignalHistoryDto toKnnSignalConfigDto(KnnSignalHistory config);

    List<KnnSignalHistoryDto> toKnnSignalConfigDto(List<KnnSignalHistory> configs);

    KnnSignalHistory toKnnSignalConfig(KnnSignalHistoryDto dto);

    List<KnnSignalHistory> toKnnSignalConfig(List<KnnSignalHistoryDto> dto);

    default KnnSignalHistoryDto toKnnSignalHistoryDto(@NonNull KLineSignal kLineSignal, @NonNull KnnSignalConfigDto config) {
        return KnnSignalHistoryDto.builder()
                .knnSignalConfigId(config.getId())
                .date(kLineSignal.getKLine().getDate())
                .code(kLineSignal.getCode())
                .knnVersion(config.getVersion())
                .signal(kLineSignal.getSignal())
                .build();
    }

    default List<KnnSignalHistoryDto> toKnnSignalHistoryDto(@NonNull List<KLineSignal> kLineSignals, @NonNull KnnSignalConfigDto config) {
        return kLineSignals.stream().map(kLineSignal -> toKnnSignalHistoryDto(kLineSignal, config)).toList();
    }

}
