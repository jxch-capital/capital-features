package org.jxch.capital.server;

import java.util.List;

public interface KnnSignalService {

    void delConfigAndHistory(List<Long> ids);

    void update(Long id);

    void updateAll(Long id);

    void updateToToday(Long id);

}
