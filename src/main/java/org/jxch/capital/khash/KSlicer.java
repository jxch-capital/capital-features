package org.jxch.capital.khash;

import org.jxch.capital.domain.dto.KLine;

import java.util.List;

public interface KSlicer {

    List<List<KLine>> slicer(List<KLine> kLines);

}
