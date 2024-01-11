package org.jxch.capital.server.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.convert.KLineMapper;
import org.jxch.capital.domain.convert.KnnSignalHistoryMapper;
import org.jxch.capital.domain.convert.SignalBackTestParamMapper;
import org.jxch.capital.domain.dto.*;
import org.jxch.capital.learning.signal.KNNSignalBackTestService;
import org.jxch.capital.learning.signal.filter.SignalFilters;
import org.jxch.capital.server.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class KnnSignalServiceImpl implements KnnSignalService {
    private final KnnSignalConfigService knnSignalConfigService;
    private final KnnSignalHistoryService knnSignalHistoryService;
    private final KNNSignalBackTestService knnSignalBackTestService;
    private final SignalBackTestParamMapper signalBackTestParamMapper;
    private final KnnSignalHistoryMapper knnSignalHistoryMapper;
    private final TransactionTemplate transactionTemplate;
    private final StockService stockService;
    private final KLineMapper kLineMapper;
    private final IndicesCombinationService indicesCombinationService;

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public void delConfigAndHistory(List<Long> ids) {
        knnSignalConfigService.del(ids);
        knnSignalHistoryService.delByConfigId(ids);
    }

    /**
     * todo 目前仅支持扩展时间范围，不支持缩减时间范围或改变到其他时间范围
     * 这个方法的可维护性为 0，要重构
     */
    @Override
    public void update(Long id) {
        TimeInterval timer = DateUtil.timer();

        KnnSignalConfigDto config = knnSignalConfigService.findById(id);
        Map<String, List<KnnSignalHistoryDto>> signalHisMap = knnSignalHistoryService.findAllByConfigId(id).stream()
                .collect(Collectors.groupingBy(KnnSignalHistoryDto::getCode));

        List<String> dbCodes = new ArrayList<>(signalHisMap.keySet());
        dbCodes.removeAll(config.getCodeList());
        List<String> delCodes = new ArrayList<>(dbCodes);
        if (!delCodes.isEmpty()) {
            delCodes.forEach(signalHisMap::remove);
        }

        List<KnnSignalHistoryDto> allDbHis = signalHisMap.values().stream().flatMap(List::stream).toList();

        Date start = config.getStartDate();
        Date end = config.getEndDate();
        List<String> configCodes = new ArrayList<>(config.getCodeList());
        configCodes.removeAll(signalHisMap.keySet());
        List<String> newCodes = new ArrayList<>(configCodes);
        List<KnnSignalHistoryDto> newCodesSignalHis = newCodes.isEmpty() ? new ArrayList<>() : backTest(newCodes, config);
        if (!newCodesSignalHis.isEmpty()) {
            if (newCodesSignalHis.get(0).getDate().getTime() < start.getTime()) {
                start = newCodesSignalHis.get(0).getDate();
            }
            if (newCodesSignalHis.get(newCodesSignalHis.size() - 1).getDate().getTime() > end.getTime()) {
                end = newCodesSignalHis.get(newCodesSignalHis.size() - 1).getDate();
            }
        }

        // 时间扩展
        List<KnnSignalHistoryDto> updateSignalHis = new ArrayList<>();
        List<KnnSignalHistoryDto> updateSignalHisInsertDtoList = new ArrayList<>();
        if (!signalHisMap.isEmpty()) {
            List<String> codes = signalHisMap.keySet().stream().toList();

            List<KnnSignalHistoryDto> firstCodeHis = signalHisMap.get(codes.get(0));
            Date dbStart = firstCodeHis.get(0).getDate();
            if (config.getStartDate().getTime() < dbStart.getTime()) {
                updateSignalHis.addAll(backTest(codes, config, getStartOffsetDay(config), dbStart));
                if (updateSignalHis.get(0).getDate().getTime() < start.getTime()) {
                    start = updateSignalHis.get(0).getDate();
                }
            }

            Date dbEnd = firstCodeHis.get(firstCodeHis.size() - 1).getDate();
            if (config.getEndDate().getTime() > dbEnd.getTime()) {
                Date startOffsetDay = getStartOffsetDay(config, dbEnd);
                updateSignalHis.addAll(backTest(codes, config, startOffsetDay, config.getEndDate()));
                if (updateSignalHis.get(updateSignalHis.size() - 1).getDate().getTime() > end.getTime()) {
                    end = updateSignalHis.get(updateSignalHis.size() - 1).getDate();
                }
            }

            if (!updateSignalHis.isEmpty()) {
                updateSignalHis.addAll(allDbHis);
                updateSignalHis = updateSignalHis.stream()
                        .collect(Collectors.toCollection(() -> new TreeSet<>(
                                Comparator.comparing(KnnSignalHistoryDto::getKnnSignalConfigId)
                                        .thenComparing(KnnSignalHistoryDto::getCode)
                                        .thenComparing(KnnSignalHistoryDto::getDate)
                        ))).stream().toList();

                // 时间对齐
                if (!newCodesSignalHis.isEmpty()) {
                    Date newCodeStart = newCodesSignalHis.get(0).getDate();
                    Date newCodeEnd = newCodesSignalHis.get(newCodesSignalHis.size() - 1).getDate();
                    Date updateSignalHisStart = updateSignalHis.get(0).getDate();
                    Date updateSignalHisEnd = updateSignalHis.get(updateSignalHis.size() - 1).getDate();
                    if (updateSignalHisStart.getTime() < newCodeStart.getTime()) {
                        updateSignalHis = updateSignalHis.stream().filter(his -> his.getDate().getTime() >= newCodeStart.getTime()).toList();
                    } else if (newCodeStart.getTime() < updateSignalHisStart.getTime()) {
                        newCodesSignalHis = newCodesSignalHis.stream().filter(his -> his.getDate().getTime() >= updateSignalHisStart.getTime()).toList();
                    }
                    if (newCodeEnd.getTime() > updateSignalHisEnd.getTime()) {
                        newCodesSignalHis = newCodesSignalHis.stream().filter(his -> his.getDate().getTime() <= updateSignalHisEnd.getTime()).toList();
                    } else if (updateSignalHisEnd.getTime() > newCodeEnd.getTime()) {
                        updateSignalHis = updateSignalHis.stream().filter(his -> his.getDate().getTime() <= newCodeEnd.getTime()).toList();
                    }
                }

                updateSignalHisInsertDtoList.addAll(updateSignalHis);
            }
        }

        Date finalEnd = end;
        Date finalStart = start;
        List<KnnSignalHistoryDto> finalNewCodesSignalHis = newCodesSignalHis;
        transactionTemplate.execute((status) -> {
            if (!delCodes.isEmpty()) {
                knnSignalHistoryService.delByConfigAndCode(id, delCodes);
            }
            if (!finalNewCodesSignalHis.isEmpty()) {
                knnSignalHistoryService.save(finalNewCodesSignalHis);
            }
            if (!updateSignalHisInsertDtoList.isEmpty()) {
                knnSignalHistoryService.delByConfigAndCode(id, new ArrayList<>(signalHisMap.keySet()));
                knnSignalHistoryService.save(updateSignalHisInsertDtoList.stream().map(dto -> dto.setId(null)).toList());
            }
            config.setLastUpdateTimeConsumingSecond(timer.intervalSecond())
                    .setLastUpdateVersion(config.getVersion())
                    .setStartDate(finalStart).setEndDate(finalEnd);
            knnSignalConfigService.save(Collections.singletonList(config));
            return null;
        });
    }

    private Date getStartOffsetDay(@NonNull KnnSignalConfigDto config) {
        return getStartOffsetDay(config, config.getStartDate());
    }

    private Date getStartOffsetDay(@NonNull KnnSignalConfigDto config, Date start) {
        return config.hasIndicesComId() ?
                stockService.getStartOffsetDay(
                        indicesCombinationService.findById(config.getIndicesComId()).getMaxLength() + config.getFutureSize() + config.getSize(),
                        start, config.getCodeList().get(0))
                : config.getStartDate();
    }

    private List<KnnSignalHistoryDto> backTest(@NonNull List<String> codes, KnnSignalConfigDto config) {
        return backTest(codes, config, getStartOffsetDay(config), config.getEndDate());
    }

    private List<KnnSignalHistoryDto> backTest(@NonNull List<String> codes, KnnSignalConfigDto config, Date startOffset, Date end) {
        return codes.stream()
                .flatMap(code -> knnSignalBackTestService.backTestByCode(
                        signalBackTestParamMapper.toSignalBackTestKNNParam(config, code, startOffset, end)).stream())
                .map(kLineSignal -> knnSignalHistoryMapper.toKnnSignalHistoryDto(kLineSignal, config))
                .toList();
    }

    /**
     * 由于存在大量耗时的计算操作，故不允许执行外部事务
     * todo 拆分
     */
    @Override
    @Transactional(value = Transactional.TxType.NEVER)
    public void updateAll(Long id) {
        TimeInterval timer = DateUtil.timer();

        KnnSignalConfigDto config = knnSignalConfigService.findById(id);
        List<KnnSignalHistoryDto> knnSignalHistoryDtoList = backTest(config.getCodeList(), config);

        transactionTemplate.execute((status) -> {
            knnSignalHistoryService.delByConfigId(config.getId());
            knnSignalHistoryService.save(knnSignalHistoryDtoList);
            config.setLastUpdateTimeConsumingSecond(timer.intervalSecond())
                    .setLastUpdateVersion(config.getVersion())
                    .setStartDate(knnSignalHistoryDtoList.get(0).getDate())
                    .setEndDate(knnSignalHistoryDtoList.get(knnSignalHistoryDtoList.size() - 1).getDate());
            knnSignalConfigService.save(Collections.singletonList(config));
            return null;
        });
    }

    @Override
    public void updateToToday(Long id) {
        knnSignalConfigService.save(Collections.singletonList(
                knnSignalConfigService.findById(id).setEndDate(Calendar.getInstance().getTime())
        ));
        update(id);
    }

    @Override
    public List<KLineSignal> findSignalHistoryToKLineSignal(Long configId, String code) {
        KnnSignalConfigDto config = knnSignalConfigService.findById(configId);
        List<KnnSignalHistoryDto> signalHistoryDtoList = knnSignalHistoryService.findAllByConfigIdAndCode(configId, code);

        List<KLine> history = stockService.history(HistoryParam.builder()
                .code(code)
                .start(DateUtil.offset(signalHistoryDtoList.get(0).getDate(), DateField.DAY_OF_YEAR, 1))
                .end(DateUtil.offset(signalHistoryDtoList.get(signalHistoryDtoList.size() - 1).getDate(), DateField.DAY_OF_YEAR, 1))
                .build());

        List<KLineSignal> kLineSignal = kLineMapper.toKLineSignal(history, config.getFutureSize(), code);

        for (int i = 0; i < kLineSignal.size(); i++) {
            if (Objects.equals(kLineSignal.get(i).getKLine().getDate().getTime(), signalHistoryDtoList.get(i).getDate().getTime())) {
                kLineSignal.get(i).setSignal(signalHistoryDtoList.get(i).getSignal());
            } else {
                throw new RuntimeException("时间没有对齐");
            }
        }

        return kLineSignal;
    }

    @Override
    public List<KLineSignal> findKLineSignal(@NonNull KnnSignalParam param) {
        List<KLineSignal> kLineSignal = findSignalHistoryToKLineSignal(param.getConfigId(), param.getCode());
        kLineSignal.forEach(ks -> ks.actionSignal(param.getSignalLimitAbs()));
        return SignalFilters.chainByFilterNames(param.getFilters(), kLineSignal);
    }

}
