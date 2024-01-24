package org.jxch.capital.support;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.utils.AppContextHolder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ServiceWrapper {
    private String name;
    private Object param;

    @JsonIgnore
    @JSONField(serialize = false)
    public ServiceWrapperSupport getService() {
        return AppContextHolder.getServiceByName(name, ServiceWrapperSupport.class);
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public <T extends ServiceWrapperSupport> T getService(@NotNull Class<T> clazz) {
        return clazz.cast(getService());
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public Object getParamObj() {
        return JSONObject.parseObject(JSONObject.toJSONString(param), getService().getDefaultParam().getClass());
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public <T> T getParamObj(@NotNull Class<T> clazz) {
        return clazz.cast(getParamObj());
    }

}
