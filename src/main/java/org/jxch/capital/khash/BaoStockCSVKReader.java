package org.jxch.capital.khash;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KLine;

import java.io.File;
import java.util.List;
import java.util.Objects;

@Slf4j
public class BaoStockCSVKReader implements KReader {
    private final CSVKReader csvkReader = CSVKReader.builder()
            .rowToKline(row -> {
                List<String> raws = row.getRawList();
                return Objects.equals(raws.get(0), "time") ? null : new KLine()
                        .setDate(DateUtil.parse(raws.get(0), "yyyyMMddHHmmssSSS"))
                        .setOpen(Double.valueOf(raws.get(1)))
                        .setHigh(Double.valueOf(raws.get(2)))
                        .setLow(Double.valueOf(raws.get(3)))
                        .setClose(Double.valueOf(raws.get(4)))
                        .setVolume(Long.valueOf(raws.get(5)));
            }).build();

    @Override
    public List<KLine> read() {
        return csvkReader.read();
    }

    public BaoStockCSVKReader setCsvFile(File csvFile) {
        csvkReader.setCsvFile(csvFile);
        return this;
    }
}
