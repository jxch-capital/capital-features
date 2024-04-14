package org.jxch.capital.khash;

import org.jxch.capital.domain.dto.KLine;

import java.util.List;

public interface KReader {

    List<KLine> read();

}
