package org.jxch.capital.http.vec.dto;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class VecDbRes implements VecRes {
    private List<Document> documents;

    public static VecDbRes valueOf(String text) {
        return VecDbRes.builder().documents(JSON.parseArray(text, Document.class)).build();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class Document {
        @JSONField(name = "page_content")
        private String pageContent;
        private Metadata metadata;
        private String type;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class Metadata {
        private String source;
    }

}
