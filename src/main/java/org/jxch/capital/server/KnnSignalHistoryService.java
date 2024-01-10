package org.jxch.capital.server;

import org.jxch.capital.domain.dto.KnnSignalHistoryDto;

import java.util.List;

public interface KnnSignalHistoryService {

    List<KnnSignalHistoryDto> findAllByConfigIdAndCode(Long configId, String code);

    Integer save(List<KnnSignalHistoryDto> dto);

    void delByConfigId(Long configId);

    void delByConfigAndCode(Long configId, String code);

    void delByConfigId(List<Long> configIds);
    void delByConfigAndCode(List<Long> configIds, List<String> codes);

}
