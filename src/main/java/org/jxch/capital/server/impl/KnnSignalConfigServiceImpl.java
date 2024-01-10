package org.jxch.capital.server.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.dao.KnnSignalConfigRepository;
import org.jxch.capital.domain.convert.KnnSignalConfigMapper;
import org.jxch.capital.domain.dto.KnnSignalConfigDto;
import org.jxch.capital.server.KnnSignalConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KnnSignalConfigServiceImpl implements KnnSignalConfigService {
    private final KnnSignalConfigRepository knnSignalConfigRepository;
    private final KnnSignalConfigMapper knnSignalConfigMapper;

    @Override
    public void update(Long id) {

    }

    @Override
    public void updateAll(Long id) {

    }

    @Override
    public void updateToToday(Long id) {

    }

    @Override
    public List<KnnSignalConfigDto> findAll() {
        return knnSignalConfigMapper.toKnnSignalConfigDto(knnSignalConfigRepository.findAll());
    }

    @Override
    public KnnSignalConfigDto findById(Long id) {
        return knnSignalConfigMapper.toKnnSignalConfigDto(knnSignalConfigRepository
                .findById(id).orElseThrow(() -> new IllegalArgumentException("数据库没有这个ID: " + id)));
    }

    @Override
    public List<KnnSignalConfigDto> findById(List<Long> ids) {
        return knnSignalConfigMapper.toKnnSignalConfigDto(knnSignalConfigRepository.findAllById(ids));
    }

    @Override
    public Integer save(List<KnnSignalConfigDto> dto) {
        return knnSignalConfigRepository.saveAllAndFlush(knnSignalConfigMapper.toKnnSignalConfig(dto)).size();
    }

    @Override
    public void del(List<Long> ids) {
        knnSignalConfigRepository.deleteAllById(ids);
    }

}
