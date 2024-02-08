package org.jxch.capital.learning.train.param;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.Objects;

public interface PredictionDataOneStockParam {

    Long getTrainConfigId();

    PredictionDataOneStockParam setTrainConfigId(Long trainConfigId);

    String getCode();

    Date getStart();

    Date getEnd();

    default boolean isSame(@NotNull PredictionDataOneStockParam param) {
        return Objects.equals(getCode(), param.getCode()) &&
                Objects.equals(getStart(), param.getStart()) &&
                Objects.equals(getEnd(), param.getEnd());
    }

}
