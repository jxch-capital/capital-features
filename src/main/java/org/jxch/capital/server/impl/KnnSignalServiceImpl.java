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
import org.jxch.capital.server.KnnSignalConfigService;
import org.jxch.capital.server.KnnSignalHistoryService;
import org.jxch.capital.server.KnnSignalService;
import org.jxch.capital.server.StockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public void delConfigAndHistory(List<Long> ids) {
        knnSignalConfigService.del(ids);
        knnSignalHistoryService.delByConfigId(ids);
    }

    @Override
    public void update(Long id) {
        // todo 改为增量更新
        updateAll(id);
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
        List<KnnSignalHistoryDto> knnSignalHistoryDtoList = config.getCodeList().stream()
                .flatMap(code -> knnSignalBackTestService.backTestByCode(
                        signalBackTestParamMapper.toSignalBackTestKNNParam(config, code)).stream())
                .map(kLineSignal -> knnSignalHistoryMapper.toKnnSignalHistoryDto(kLineSignal, config))
                .toList();

        transactionTemplate.execute((status) -> {
            knnSignalHistoryService.delByConfigId(config.getId());
            knnSignalHistoryService.save(knnSignalHistoryDtoList);
            config.setLastUpdateTimeConsumingSecond(timer.intervalSecond()).setLastUpdateVersion(config.getVersion());
            knnSignalConfigService.save(Collections.singletonList(config));
            return null;
        });
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
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
