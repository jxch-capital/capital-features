package org.jxch.capital.server.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.dao.WatchConfigRepository;
import org.jxch.capital.domain.convert.WatchConfigMapper;
import org.jxch.capital.domain.dto.WatchConfigDto;
import org.jxch.capital.server.WatchConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WatchConfigServiceImpl implements WatchConfigService {
    private final WatchConfigRepository watchConfigRepository;
    private final WatchConfigMapper watchConfigMapper;


    @Override
    public List<WatchConfigDto> findAll() {
        return watchConfigMapper.toWatchConfigDto(watchConfigRepository.findAll());
    }

    @Override
    public WatchConfigDto findById(Long id) {
        return watchConfigMapper.toWatchConfigDto(watchConfigRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("没有这个订阅ID: " + id)));
    }

    @Override
    public List<WatchConfigDto> findById(List<Long> ids) {
        return watchConfigMapper.toWatchConfigDto(watchConfigRepository.findAllById(ids));
    }

    @Override
    public List<WatchConfigDto> findByUserId(Long userid) {
        return watchConfigMapper.toWatchConfigDto(watchConfigRepository.findAllByUserId(userid));
    }

    @Override
    public List<WatchConfigDto> findByWatchName(String watchName) {
        return watchConfigMapper.toWatchConfigDto(watchConfigRepository.findAllByWatchName(watchName));
    }

    @Override
    public boolean userHasWatch(Long userid, String watchName) {
        return !watchConfigRepository.findAllByUserIdAndWatchName(userid, watchName).isEmpty();
    }

    @Override
    public void del(Long id) {
        watchConfigRepository.deleteById(id);
    }

    @Override
    public void del(List<Long> ids) {
        watchConfigRepository.deleteAllById(ids);
    }

    @Override
    public Integer save(List<WatchConfigDto> dtoList) {
        return watchConfigRepository.saveAllAndFlush(watchConfigMapper.toWatchConfig(dtoList)).size();
    }

}
