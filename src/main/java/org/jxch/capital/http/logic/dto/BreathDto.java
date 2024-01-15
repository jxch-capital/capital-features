package org.jxch.capital.http.logic.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BreathDto {
    private List<Item> scores = new ArrayList<>();

    public BreathDto add(LocalDate date, String type, Integer score) {
        scores.add(Item.builder().date(date).type(type).score(score).build());
        return this;
    }

    @JSONField(serialize = false)
    public BreathDto.Item getItem(String type, LocalDate date) {
        return scores.stream().filter(item -> Objects.equals(type, item.getType()) && Objects.equals(date, item.getDate()))
                .findAny().orElseThrow(() -> new IllegalArgumentException("没有这个数据：" + type + " - " + date.toString()));
    }

    @JSONField(serialize = false)
    public List<BreathDto.Item> getItems(String type) {
        return scores.stream().filter(item -> Objects.equals(type, item.getType()))
                .sorted(Comparator.comparing(Item::getDate).reversed())
                .toList();
    }

    @JSONField(serialize = false)
    public List<BreathDto.Item> getItems(LocalDate date) {
        return scores.stream().filter(item -> Objects.equals(date, item.getDate()))
                .sorted(Comparator.comparing(Item::getType))
                .toList();
    }

    @JSONField(serialize = false)
    public List<String> getTypes() {
        return scores.stream().map(Item::getType).distinct().sorted().toList();
    }

    @JSONField(serialize = false)
    public List<LocalDate> getDates() {
        return scores.stream().map(Item::getDate).distinct().sorted(Comparator.reverseOrder()).toList();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class Item {
        private LocalDate date;
        private String type;
        private Integer score;
    }

}
