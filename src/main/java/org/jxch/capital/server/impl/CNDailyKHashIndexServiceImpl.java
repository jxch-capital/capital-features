package org.jxch.capital.server.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.dao.CNDailyKHashIndexRepository;
import org.jxch.capital.domain.convert.CNDailyKHashIndexMapper;
import org.jxch.capital.domain.dto.CNDailyKHashIndexDto;
import org.jxch.capital.khash.DailyGridKHashCNDailyIndexAgg;
import org.jxch.capital.khash.KReader;
import org.jxch.capital.server.CNDailyKHashIndexService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CNDailyKHashIndexServiceImpl implements CNDailyKHashIndexService {
    private final CNDailyKHashIndexRepository cnDailyKHashIndexRepository;
    private final CNDailyKHashIndexMapper cnDailyKHashIndexMapper;

    @Override
    public Integer saveByAgg(@NotNull KReader reader, @NotNull DailyGridKHashCNDailyIndexAgg agg) {
        List<CNDailyKHashIndexDto> kHashIndexDtoList = agg.aggregate(reader.read()).values().stream().flatMap(List::stream).toList();
        return cnDailyKHashIndexRepository.saveAll(cnDailyKHashIndexMapper.toCNDailyKHashIndex(kHashIndexDtoList)).size();
    }

    @Override
    public List<List<CNDailyKHashIndexDto>> findByAgg(@NotNull KReader reader, @NotNull DailyGridKHashCNDailyIndexAgg agg) {
        return agg.aggregate(reader.read()).values().stream().flatMap(List::stream)
                .map(dto -> findBySubHash(dto.getHash(), agg.getDailyGridKHashKLinesAgg().hashLength(), dto.getIsFillLength(), dto.getLeftVacancies())).toList();
    }

    @Override
    public List<Page<CNDailyKHashIndexDto>> findByAgg(@NotNull KReader reader, @NotNull DailyGridKHashCNDailyIndexAgg agg, Pageable pageable) {
        return agg.aggregate(reader.read()).values().stream().flatMap(List::stream)
                .map(dto -> findBySubHash(dto.getHash(), agg.getDailyGridKHashKLinesAgg().hashLength(), dto.getIsFillLength(), dto.getLeftVacancies(), pageable)).toList();
    }

    @NotNull
    private String fillAppendHash(@NotNull BigDecimal subHash, Integer hashLength, Integer leftVacancies) {
        return new String(new char[hashLength - leftVacancies - String.valueOf(subHash).length()]).replace("\0", "0");
    }

    @NotNull
    private Long fromByHash(@NotNull BigDecimal subHash, Integer hashLength, Integer leftVacancies) {
        return Long.valueOf(subHash + fillAppendHash(subHash, hashLength, leftVacancies));
    }

    @NotNull
    private Long toByHash(@NotNull BigDecimal subHash, Integer hashLength, Integer leftVacancies) {
        return Long.valueOf((subHash.add(new BigDecimal(1))) + fillAppendHash(subHash, hashLength, leftVacancies));
    }

    @Override
    public List<CNDailyKHashIndexDto> findBySubHash(BigDecimal subHash, Integer hashLength, @NotNull Boolean isFillLength, Integer leftVacancies) {
        Long from = fromByHash(subHash, hashLength, leftVacancies);
        Long to = toByHash(subHash, hashLength, leftVacancies);
        return isFillLength ?
                cnDailyKHashIndexMapper.toCNDailyKHashIndexDto(cnDailyKHashIndexRepository.findAllByHashBetweenAndAndIsFillLengthAndLeftVacancies(from, to, true, leftVacancies)) :
                cnDailyKHashIndexMapper.toCNDailyKHashIndexDto(cnDailyKHashIndexRepository.findAllByHashBetweenAndAndIsFillLength(from, to, false));
    }

    @Override
    public Page<CNDailyKHashIndexDto> findBySubHash(BigDecimal subHash, Integer hashLength, Boolean isFillLength, Integer leftVacancies, Pageable pageable) {
        Long from = fromByHash(subHash, hashLength, leftVacancies);
        Long to = toByHash(subHash, hashLength, leftVacancies);
        return isFillLength ?
                cnDailyKHashIndexRepository.findAllByHashBetweenAndAndIsFillLengthAndLeftVacancies(from, to, true, leftVacancies, pageable).map(cnDailyKHashIndexMapper::toCNDailyKHashIndexDto) :
                cnDailyKHashIndexRepository.findAllByHashBetweenAndAndIsFillLength(from, to, false, pageable).map(cnDailyKHashIndexMapper::toCNDailyKHashIndexDto);
    }

    @Override
    public List<CNDailyKHashIndexDto> findBySubHash(BigDecimal subHash, Integer hashLength) {
        return findBySubHash(subHash, hashLength, false, 0);
    }

    @Override
    public Page<CNDailyKHashIndexDto> findBySubHash(BigDecimal subHash, Integer hashLength, Pageable pageable) {
        return findBySubHash(subHash, hashLength, false, 0, pageable);
    }

}
