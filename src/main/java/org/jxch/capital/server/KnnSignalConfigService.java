package org.jxch.capital.server;

import org.jxch.capital.domain.dto.KnnSignalConfigDto;

import java.util.List;

public interface KnnSignalConfigService {

    List<KnnSignalConfigDto> findAll();

    List<KnnSignalConfigDto> findAllUpdated();

    KnnSignalConfigDto findById(Long id);

    List<KnnSignalConfigDto> findById(List<Long> ids);

    Integer save(List<KnnSignalConfigDto> dto);

    void del(List<Long> ids);

}
