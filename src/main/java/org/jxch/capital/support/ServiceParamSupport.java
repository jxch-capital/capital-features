package org.jxch.capital.support;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface ServiceParamSupport<T> {

    default T getDefaultParam() {
        return null;
    }

    default String getDefaultParamJson() {
        return JSONObject.toJSONString(getDefaultParam(), JSONWriter.Feature.WriteMapNullValue, JSONWriter.Feature.WriteNullListAsEmpty);
    }

    default void setDefaultParamJsonIfBlank(@NotNull Supplier<String> supplier, Consumer<String> consumer) {
        if (Objects.isNull(supplier.get()) || supplier.get().isBlank()) {
            consumer.accept(getDefaultParamJson());
        }
    }

}
