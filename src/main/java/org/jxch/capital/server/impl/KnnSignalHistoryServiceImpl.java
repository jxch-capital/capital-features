package org.jxch.capital.server.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.dao.KnnSignalHistoryRepository;
import org.jxch.capital.domain.convert.KnnSignalHistoryMapper;
import org.jxch.capital.domain.dto.KnnSignalHistoryDto;
import org.jxch.capital.server.KnnSignalHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KnnSignalHistoryServiceImpl implements KnnSignalHistoryService {
    private final KnnSignalHistoryMapper knnSignalHistoryMapper;
    private final KnnSignalHistoryRepository knnSignalHistoryRepository;

    @Override
    public List<KnnSignalHistoryDto> findAllByConfigIdAndCode(Long configId, String code) {
        return knnSignalHistoryMapper.toKnnSignalConfigDto(
                knnSignalHistoryRepository.findKnnSignalHistoriesByKnnSignalConfigIdAndCode(configId, code));
    }

    @Override
    public Integer save(List<KnnSignalHistoryDto> dto) {
        return knnSignalHistoryRepository.saveAllAndFlush(knnSignalHistoryMapper.toKnnSignalConfig(dto)).size();
    }

    @Override
    public void delByConfigId(Long configId) {
        knnSignalHistoryRepository.deleteAllByKnnSignalConfigId(configId);
    }

    @Override
    public void delByConfigAndCode(Long configId, String code) {
        knnSignalHistoryRepository.deleteAllByKnnSignalConfigIdAndCode(configId, code);
    }

    @Override
    public void delByConfigId(List<Long> configIds) {
        knnSignalHistoryRepository.delByConfigId(configIds);
    }

    @Override
    public void delByConfigAndCode(List<Long> configIds, List<String> codes) {
        knnSignalHistoryRepository.delByConfigIdAndCode(configIds, codes);
    }

}
