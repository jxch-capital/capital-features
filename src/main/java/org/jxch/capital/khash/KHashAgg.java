package org.jxch.capital.khash;

import org.jxch.capital.domain.dto.KLine;

import java.util.List;
import java.util.Map;

public interface KHashAgg<T> {

    Map<String, List<T>> aggregate(List<KLine> kLines);

}
