package org.jxch.capital.subscriber.convert;

import org.jxch.capital.http.logic.dto.BreathParam;
import org.jxch.capital.subscriber.dto.BreathSubscriberParam;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParamConvertMapper {

    BreathParam toBreathParam(BreathSubscriberParam param);

}
