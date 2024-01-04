package org.jxch.capital.server.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.dao.IndicesCombinationRepository;
import org.jxch.capital.domain.convert.IndicesCombinationMapper;
import org.jxch.capital.domain.dto.IndicatorWrapper;
import org.jxch.capital.domain.dto.IndicesCombinationDto;
import org.jxch.capital.domain.dto.IndicesConfigDto;
import org.jxch.capital.server.IndicesCombinationService;
import org.jxch.capital.server.IndicesConfigService;
import org.springframework.stereotype.Service;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.num.Num;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class IndicesCombinationServiceImpl implements IndicesCombinationService {
    private final IndicesCombinationRepository indicesCombinationRepository;
    private final IndicesCombinationMapper indicesCombinationMapper;
    private final IndicesConfigService indicesConfigService;

    private IndicesCombinationDto setIndicesCombinationDtoNames(@NonNull IndicesCombinationDto dto) {
        return dto.setIndicesNameByList(
                indicesConfigService.findById(dto.getIndicesIdList()).stream().map(IndicesConfigDto::getName).toList());
    }

    @Override
    public List<IndicesCombinationDto> findAll() {
        return indicesCombinationMapper.toIndicesCombinationDto(indicesCombinationRepository.findAll()).stream()
                .map(this::setIndicesCombinationDtoNames).toList();
    }

    @Override
    public IndicesCombinationDto findById(Long id) {
        return setIndicesCombinationDtoNames(
                indicesCombinationMapper.toIndicesCombinationDto(indicesCombinationRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("数据库中没有这个指标组合, ID: " + id))));
    }

    @Override
    public List<IndicesCombinationDto> findById(List<Long> ids) {
        return indicesCombinationMapper.toIndicesCombinationDto(indicesCombinationRepository.findAllById(ids)).stream()
                .map(this::setIndicesCombinationDtoNames).toList();
    }

    @Override
    public Integer save(List<IndicesCombinationDto> dto) {
        return indicesCombinationRepository.saveAllAndFlush(indicesCombinationMapper.toIndicesCombination(dto)).size();
    }

    @Override
    public void del(List<Long> ids) {
        indicesCombinationRepository.deleteAllById(ids);
    }

    @Override
    public List<Indicator<Num>> getIndicatorsById(Long id, BarSeries barSeries) {
        return findById(id).getIndicesIdList().stream()
                .map(indexId -> indicesConfigService.getIndicatorById(indexId, barSeries)).toList();
    }

    @Override
    public List<IndicatorWrapper> getIndicatorWrapper(Long id) {
        return findById(id).getIndicesIdList().stream()
                .map(indexId -> IndicatorWrapper.builder()
                        .name(indicesConfigService.findById(indexId).getName())
                        .indicatorFunc(barSeries -> indicesConfigService.getIndicatorById(indexId, barSeries))
                        .build())
                .toList();
    }

}
