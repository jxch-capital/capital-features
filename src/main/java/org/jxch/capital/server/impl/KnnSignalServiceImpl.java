package org.jxch.capital.server.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.convert.KnnSignalHistoryMapper;
import org.jxch.capital.domain.convert.SignalBackTestParamMapper;
import org.jxch.capital.domain.dto.KnnSignalConfigDto;
import org.jxch.capital.domain.dto.KnnSignalHistoryDto;
import org.jxch.capital.learning.signal.KNNSignalBackTestService;
import org.jxch.capital.server.KnnSignalConfigService;
import org.jxch.capital.server.KnnSignalHistoryService;
import org.jxch.capital.server.KnnSignalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

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

}
