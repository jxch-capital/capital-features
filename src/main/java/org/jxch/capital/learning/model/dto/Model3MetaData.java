package org.jxch.capital.learning.model.dto;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.io.dto.FileMetaData;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 字段名称必须使用小写，因为Minio会自动将元数据全部转为小写
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Model3MetaData {
    private String filename;
    private Long trainconfigid;
    private String remark;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date uploadtime = Calendar.getInstance().getTime();


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

    public static Model3MetaData parseOf(@NotNull FileMetaData fileMetaData) {
        return JSONObject.parseObject(JSONObject.toJSONString(fileMetaData.getMetaData()), Model3MetaData.class)
                .setFilename(fileMetaData.getFileName());
    }

}
