package org.jxch.capital.khash;

import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import lombok.Builder;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KLine;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Slf4j
@Builder
public class CSVKReader implements KReader {
    @Setter
    private File csvFile;
    private final Function<CsvRow, KLine> rowToKline;

    @Override
    public List<KLine> read() {
        return CsvUtil.getReader().read(csvFile).getRows().stream()
                .map(rowToKline).filter(Objects::nonNull).toList();
    }

}
