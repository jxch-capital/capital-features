package org.jxch.capital.domain.convert;

import org.jxch.capital.domain.dto.HistoryParam;
import org.jxch.capital.http.yahoo.dto.ChartParam;
import org.jxch.capital.http.yahoo.dto.DownloadStockCsvParam;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HistoryParamMapper {

    DownloadStockCsvParam toDownloadStockCsvParam(HistoryParam param);

    ChartParam toChartParam(HistoryParam param);

}
