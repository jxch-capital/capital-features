package org.jxch.capital.domain.convert;

import lombok.NonNull;
import org.jxch.capital.domain.dto.KNNParam;
import org.jxch.capital.domain.dto.KNodeParam;
import org.jxch.capital.domain.dto.KnnSignalConfigDto;
import org.jxch.capital.learning.signal.dto.SignalBackTestKNNParam;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SignalBackTestParamMapper {

    default SignalBackTestKNNParam toSignalBackTestKNNParam(@NonNull KnnSignalConfigDto config, String code) {
        return (SignalBackTestKNNParam) SignalBackTestKNNParam.emptyParam()
                .setFutureNum(config.getFutureSize())
                .setKnnParam(KNNParam.builder()
                        .neighborSize(config.getNeighborSize())
                        .distanceName(config.getDistance())
                        .kNodeParam(KNodeParam.builder()
                                .size(config.getSize())
                                .indicesComId(config.getIndicesComId())
                                .stockPoolId(config.getStockPoolId())
                                .normalized(config.getIsNormalized())
                                .build()
                        ).build())
                .setStart(config.getStartDate())
                .setEnd(config.getEndDate())
                .setSignalLimitAbs(0)
                .setCode(code);
    }

}
