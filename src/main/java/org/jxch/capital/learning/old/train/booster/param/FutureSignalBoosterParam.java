package org.jxch.capital.learning.old.train.booster.param;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.learning.old.train.param.SignalType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class FutureSignalBoosterParam {
    private String signalType = SignalType.UP.toString();
    private Integer offset = 1;

    @JsonIgnore
    @JSONField(serialize = false)
    public SignalType getSType() {
        return SignalType.valueOf(signalType);
    }

}
