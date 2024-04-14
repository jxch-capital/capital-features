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
    public List<CNDailyKHashIndexDto> findByAgg(@NotNull KReader reader, @NotNull DailyGridKHashCNDailyIndexAgg agg) {
        return agg.aggregate(reader.read()).values().stream().flatMap(List::stream)
                .flatMap(dto -> findBySubHash(dto.getHash(), agg.getDailyGridKHashKLinesAgg().hashLength(), dto.getIsFillLength(), dto.getLeftVacancies()).stream()).toList();
    }

    @Override
    public List<CNDailyKHashIndexDto> findBySubHash(BigDecimal subHash, Integer hashLength, @NotNull Boolean isFillLength, Integer leftVacancies) {
        String fillAppend = new String(new char[hashLength - leftVacancies - String.valueOf(subHash).length()]).replace("\0", "0");
        Long from = Long.valueOf(subHash + fillAppend);
        Long to = Long.valueOf((subHash.add(new BigDecimal(1))) + fillAppend);
        return isFillLength ?
                cnDailyKHashIndexMapper.toCNDailyKHashIndexDto(cnDailyKHashIndexRepository.findAllByHashBetweenAndAndIsFillLengthAndLeftVacancies(from, to, true, leftVacancies)) :
                cnDailyKHashIndexMapper.toCNDailyKHashIndexDto(cnDailyKHashIndexRepository.findAllByHashBetweenAndAndIsFillLength(from, to, false));
    }

    @Override
    public List<CNDailyKHashIndexDto> findBySubHash(BigDecimal subHash, Integer hashLength) {
        return findBySubHash(subHash, hashLength, false, 0);
    }

}
