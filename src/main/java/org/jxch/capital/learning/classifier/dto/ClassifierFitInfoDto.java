package org.jxch.capital.learning.classifier.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.lang.reflect.Method;
import java.util.Arrays;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ClassifierFitInfoDto implements Cloneable {
    private String classifierName;
    @Builder.Default
    @JSONField(serialize = false)
    private Class<?> classifierClazz = null;
    @Builder.Default
    @JSONField(serialize = false)
    private Method fitMethod = null;
    @Builder.Default
    private String classifierParamTypes = null;

    public ClassifierFitInfoDto init() {
        classifierParamTypes = String.join(",", Arrays.stream(fitMethod.getParameterTypes()).map(Class::getSimpleName).toList());
        return this;
    }

    @Override
    public ClassifierFitInfoDto clone() {
        try {
            return (ClassifierFitInfoDto) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
