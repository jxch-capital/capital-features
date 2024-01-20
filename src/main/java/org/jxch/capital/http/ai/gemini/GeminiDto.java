package org.jxch.capital.http.ai.gemini;

import com.alibaba.fastjson2.JSONObject;
import lombok.*;
import lombok.experimental.Accessors;
import org.jxch.capital.http.ai.TextAiParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GeminiDto implements TextAiParam {
    /**
     * 查询参数
     */
    @Builder.Default
    private List<Content> contents = new ArrayList<>();
    @Builder.Default
    private GenerationConfig generationConfig = GenerationConfig.builder().build();
    @Builder.Default
    private List<SafetySetting> safetySettings = new ArrayList<>(Arrays.asList(
            SafetySetting.builder().category("HARM_CATEGORY_HARASSMENT").threshold("BLOCK_MEDIUM_AND_ABOVE").build(),
            SafetySetting.builder().category("HARM_CATEGORY_HATE_SPEECH").threshold("BLOCK_MEDIUM_AND_ABOVE").build(),
            SafetySetting.builder().category("HARM_CATEGORY_SEXUALLY_EXPLICIT").threshold("BLOCK_MEDIUM_AND_ABOVE").build(),
            SafetySetting.builder().category("HARM_CATEGORY_DANGEROUS_CONTENT").threshold("BLOCK_MEDIUM_AND_ABOVE").build()
    ));

    /**
     * 响应值
     */
    @Builder.Default
    private Integer index = null;
    @Builder.Default
    private String finishReason = null;
    @Builder.Default
    private Content content = null;

    public GeminiDto addContentPartText(String text) {
        this.contents.add(Content.builder().build().addPartByText(text));
        return this;
    }

    public GeminiDto addLastContentPartText(String text) {
        if (contents.isEmpty()) {
            addContentPartText(text);
        } else {
            this.contents.get(contents.size() - 1).addPartByText(text);
        }

        return this;
    }

    public GeminiDto addContentPartText(String text, int index) {
        this.contents.get(index).addPartByText(text);
        return this;
    }

    public GeminiDto clearRes() {
        this.index = null;
        this.finishReason = null;
        this.content = null;
        this.contents.forEach(con -> con.role = null);
        return this;
    }

    public String toJsonString() {
        return JSONObject.toJSONString(this);
    }

    public static GeminiDto getGeminiParam(String text) {
        return GeminiDto.builder().build().addContentPartText(text);
    }

    public static String getGeminiParamString(String text) {
        return getGeminiParam(text).toJsonString();
    }

    public static GeminiDto getGeminiDto(@NonNull GeminiDto geminiDto, @NonNull GeminiRes res) {
        return geminiDto.addLastContentPartText(res.getResString()).clearRes();
    }

    public static GeminiDto getGeminiParam(String text, @NonNull GeminiDto geminiDto, @NonNull GeminiRes res) {
        return getGeminiParam(text, getGeminiDto(geminiDto, res));
    }

    public static GeminiDto getGeminiParam(String text, @NonNull GeminiDto geminiDto) {
        return geminiDto.addLastContentPartText(text).clearRes();
    }

    @Override
    public <T> T getParam(@NonNull Class<T> clazz) {
        return clazz.cast(this);
    }

    @Override
    public TextAiParam addText(String text) {
        return this.addLastContentPartText(text);
    }

    @Override
    public List<String> chainText() {
        return this.contents.stream().flatMap(con -> con.getParts().stream().map(Part::getText)).toList();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class Content {
        @Builder.Default
        private List<Part> parts = new ArrayList<>();
        @Builder.Default
        private String role = null;

        public Content addPart(Part part) {
            parts.add(part);
            return this;
        }

        public Content addPartByText(String text) {
            return addPart(Part.valueOf(text));
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class Part {
        private String text;

        public static Part valueOf(@NonNull Object obj) {
            return Part.builder().text(obj.toString()).build();
        }

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class GenerationConfig {
        @Builder.Default
        private double temperature = 0.9;
        @Builder.Default
        private int topK = 1;
        @Builder.Default
        private int topP = 1;
        @Builder.Default
        private int maxOutputTokens = 2048;
        @Builder.Default
        private List<String> stopSequences = new ArrayList<>();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class SafetySetting {
        private String category;
        private String threshold;
    }

}
