package org.jxch.capital.server;

import org.jxch.capital.domain.dto.KLineSignal;
import org.jxch.capital.domain.dto.KnnSignalParam;

import java.util.List;

public interface KnnSignalService {

    void delConfigAndHistory(List<Long> ids);

    void update(Long id);

    void updateAll(Long id);

    void updateToToday(Long id);

    List<KLineSignal> findSignalHistoryToKLineSignal(Long configId, String code);

    List<KLineSignal> findKLineSignal(KnnSignalParam param);

}
