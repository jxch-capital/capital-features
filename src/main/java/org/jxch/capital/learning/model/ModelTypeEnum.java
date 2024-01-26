package org.jxch.capital.learning.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jxch.capital.learning.model.dto.Model3BaseMetaData;
import org.jxch.capital.learning.model.dto.TensorflowTFModelMetaData;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum ModelTypeEnum {
    TENSORFLOW_MODEL_TF_ZIP("tensorflow_model_tf_zip", TensorflowTFModelMetaData.class),
    TENSORFLOW_MODEL_H5_ZIP("tensorflow_model_h5_zip", Model3BaseMetaData.class),
    ;
    private final String name;
    private final Class<? extends Model3BaseMetaData> metaDataClazz;

    public static ModelTypeEnum parseOf(String name) {
        return Arrays.stream(ModelTypeEnum.values()).filter(modelTypeEnum -> Objects.equals(name, modelTypeEnum.getName()))
                .findAny().orElseThrow(() -> new IllegalArgumentException("没有这种类型：" + name));
    }

    public boolean isZip() {
        return Objects.equals(this, TENSORFLOW_MODEL_H5_ZIP) || Objects.equals(this, TENSORFLOW_MODEL_TF_ZIP);
    }

    public boolean isTensorflowModel() {
        return Objects.equals(this, TENSORFLOW_MODEL_H5_ZIP) || Objects.equals(this, TENSORFLOW_MODEL_TF_ZIP);
    }

}
