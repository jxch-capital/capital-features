package org.jxch.capital.stock;

import org.jxch.capital.domain.dto.K5MCNCsvDto;
import org.jxch.capital.influx.point.K5MCNInfluxPoint;

import java.io.File;
import java.util.List;
import java.util.function.Function;

public interface K5MCNInfluxService {

    Integer saveAll(List<K5MCNInfluxPoint> points);

    Integer saveAllByCsv(List<K5MCNCsvDto> csvDtoList, String code);

    Integer saveAllByCsvFiles(List<File> csvFiles);

    Integer saveAllByCsvFiles(List<File> csvFiles, Function<File, String> codeGetter);

}
