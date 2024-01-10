package org.jxch.capital.server;

import lombok.NonNull;
import org.jxch.capital.domain.dto.KnnSignalConfigDto;

import java.util.List;

public interface KnnSignalConfigService {

    List<KnnSignalConfigDto> findAll();

    KnnSignalConfigDto findById(Long id);

    List<KnnSignalConfigDto> findById(List<Long> ids);

    Integer save(List<KnnSignalConfigDto> dto);

    void del(List<Long> ids);

    void update(Long id);

    void updateAll(Long id);

    void updateToToday(Long id);

    default void update(@NonNull List<Long> ids) {
        ids.forEach(this::update);
    }

    default void updateAll(@NonNull List<Long> ids) {
        ids.forEach(this::updateAll);
    }

    default void updateToToday(@NonNull List<Long> ids) {
        ids.forEach(this::updateToToday);
    }

}
