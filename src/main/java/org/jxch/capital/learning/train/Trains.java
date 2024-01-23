package org.jxch.capital.learning.train;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.domain.dto.TrainConfigDto;
import org.jxch.capital.utils.AppContextHolder;

import java.util.List;
import java.util.Objects;

public class Trains {

    public static List<TrainDataService> allTrainDataService() {
        return AppContextHolder.getContext().getBeansOfType(TrainDataService.class).values().stream().toList();
    }

    public static List<String> allTrainDataServiceNames() {
        return allTrainDataService().stream().map(TrainDataService::name).toList();
    }

    public static TrainDataService getTrainDataService(String name) {
        return allTrainDataService().stream().filter(trainDataService -> Objects.equals(trainDataService.name(), name))
                .findAny().orElseThrow(() -> new IllegalArgumentException("没有这个训练服务"));
    }

    @NotNull
    @Contract("_ -> param1")
    public static TrainConfigDto setDefaultParams(@NonNull TrainConfigDto dto) {
        if (Objects.isNull(dto.getParams()) || dto.getParams().isBlank()) {
            dto.setParams(JSONObject.toJSONString(getTrainDataService(dto.getService()).getDefaultParam(), JSONWriter.Feature.WriteMapNullValue, JSONWriter.Feature.WriteNullListAsEmpty));
        }
        return dto;
    }

}
