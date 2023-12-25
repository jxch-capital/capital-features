package org.jxch.capital.server;

import org.jxch.capital.domain.dto.HistoryParam;
import org.jxch.capital.domain.dto.KLine;

import java.util.List;

public interface StockService {
    List<KLine> history(HistoryParam param);
}
