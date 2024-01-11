package org.jxch.capital.domain.convert;

import lombok.NonNull;
import org.jxch.capital.domain.dto.KnnSignalParam;
import org.jxch.capital.domain.dto.NKnnSignalParam;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface KnnSignalParamMapper {

    default List<KnnSignalParam> toNKnnSignalParam(@NonNull NKnnSignalParam param) {
        return param.getConfigIds().stream().map(id -> org.jxch.capital.domain.dto.KnnSignalParam.builder()
                .configId(id)
                .filters(param.getFilters())
                .code(param.getCode())
                .signalLimitAbs(param.getSignalLimitAbs())
                .build()).toList();
    }

}
