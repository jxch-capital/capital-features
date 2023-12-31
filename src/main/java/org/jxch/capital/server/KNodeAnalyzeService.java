package org.jxch.capital.server;

import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.domain.dto.KLineAnalyzeStatistics;
import org.jxch.capital.domain.dto.KLineAnalyzedParam;
import org.jxch.capital.domain.dto.KLineAnalyzes;

import java.util.List;

public interface KNodeAnalyzeService {

    List<KLine> search(KLineAnalyzedParam param);

    KLineAnalyzes analyze(KLineAnalyzedParam param);

    KLineAnalyzeStatistics statistics(List<KLineAnalyzes> analyzes);

}
