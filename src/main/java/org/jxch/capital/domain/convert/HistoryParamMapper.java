package org.jxch.capital.domain.convert;

import org.jxch.capital.domain.dto.HistoryDBParam;
import org.jxch.capital.domain.dto.HistoryParam;
import org.jxch.capital.http.yahoo.dto.ChartParam;
import org.jxch.capital.http.yahoo.dto.DownloadStockCsvParam;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface HistoryParamMapper {

    DownloadStockCsvParam toDownloadStockCsvParam(HistoryParam param);

    ChartParam toChartParam(HistoryParam param);

    HistoryDBParam toHistoryDBParam(HistoryParam param);

}
