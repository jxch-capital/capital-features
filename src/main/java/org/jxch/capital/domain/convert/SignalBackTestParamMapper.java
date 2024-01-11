package org.jxch.capital.domain.convert;

import lombok.NonNull;
import org.jxch.capital.domain.dto.KNNParam;
import org.jxch.capital.domain.dto.KNodeParam;
import org.jxch.capital.domain.dto.KnnSignalConfigDto;
import org.jxch.capital.learning.signal.dto.SignalBackTestKNNParam;
import org.mapstruct.Mapper;

import java.util.Date;

@Mapper(componentModel = "spring")
public interface SignalBackTestParamMapper {

    default SignalBackTestKNNParam toSignalBackTestKNNParam(@NonNull KnnSignalConfigDto config, String code) {
        return toSignalBackTestKNNParam(config, code, config.getStartDate());
    }

    default SignalBackTestKNNParam toSignalBackTestKNNParam(@NonNull KnnSignalConfigDto config, String code, Date startOffset) {
        return toSignalBackTestKNNParam(config, code, startOffset, config.getEndDate());
    }

    default SignalBackTestKNNParam toSignalBackTestKNNParam(@NonNull KnnSignalConfigDto config, String code, Date startOffset, Date end) {
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
                .setStart(startOffset)
                .setEnd(end)
                .setSignalLimitAbs(0)
                .setCode(code);
    }

}
