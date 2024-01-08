package org.jxch.capital.knn.filter;

import org.jxch.capital.domain.dto.KLineIndices;

@FunctionalInterface
public interface ResetSignalFunction {

    Boolean can(KLineIndices indices, Integer signal);

}
