package org.jxch.capital.learning.model.dto;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.io.dto.FileMetaData;
import org.jxch.capital.learning.model.ModelStorageTypeEnum;
import org.jxch.capital.learning.model.ModelTypeEnum;
import org.jxch.capital.learning.model.PredictSignalTypeEnum;
import org.jxch.capital.utils.JSONUtils;
import org.jxch.capital.utils.ReflectionsU;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 字段名称必须使用小写，因为Minio会自动将元数据全部转为小写
 * 值只能使用字符串类型
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Model3BaseMetaData {
    private String filename;
    private Long trainconfigid;
    private String modeltype = ModelTypeEnum.TENSORFLOW_MODEL_TF_ZIP.getName();
    private String storagetype = ModelStorageTypeEnum.MINIO.getName();
    private String predictsignaltype = PredictSignalTypeEnum.FOLLOW_UP_DOWN.getName();
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date uploadtime = Calendar.getInstance().getTime();
    private String remark;

    @JsonIgnore
    @JSONField(serialize = false)
    public Map<String, String> toMap() {
        return JSONObject.parseObject(JSONObject.toJSONString(this))
                .entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> entry.getValue().toString()));
    }

    public FileMetaData toFileMetaData() {
        return FileMetaData.builder()
                .fileName(filename)
                .metaData(toMap())
                .build();
    }

    public static Model3BaseMetaData parseOf(@NotNull FileMetaData fileMetaData) {
        return parseOfJson(JSONObject.toJSONString(fileMetaData.getMetaData()))
                .setFilename(fileMetaData.getFileName());
    }

    public static Model3BaseMetaData parseOfJson(String metaDataJson) {
        var metaDataClazz = ModelTypeEnum.parseOf(JSONObject.parseObject(metaDataJson, Model3BaseMetaData.class).getModeltype()).getMetaDataClazz();
        return JSONObject.parseObject(metaDataJson, metaDataClazz);
    }

    public static Map<String, String> allModelMetaDataJson() {
       return Arrays.stream(ModelTypeEnum.values()).collect(Collectors.toMap(ModelTypeEnum::getName,
               type -> JSONUtils.toJsonAndNullPretty(ReflectionsU.newInstance(type.getMetaDataClazz()).setModeltype(type.getName()))));
    }

}
