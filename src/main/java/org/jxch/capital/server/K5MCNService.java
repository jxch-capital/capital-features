package org.jxch.capital.server;

import org.jxch.capital.domain.dto.K5MCNDto;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

public interface K5MCNService {

    Integer saveAll(List<K5MCNDto> k5MCNDtoList);

    Integer saveAllByCsvFiles(List<File> csvFiles, Function<File, String> codeGetter);

    Integer saveAllByCsvFiles(List<File> csvFiles);

    List<K5MCNDto> findByCodeAndTimeBetween(String code, Date start, Date end);

}
