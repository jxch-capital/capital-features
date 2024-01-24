package org.jxch.capital.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class KLineIndices extends KLine {

    private Map<String, Double> indices = new TreeMap<>();

    public void setIndex(String name, Double value) {
        indices.put(name, value);
    }

    public Double get(String name) {
        return indices.get(name);
    }

    public List<Double> get(@NotNull List<String> names) {
        return names.stream().map(indices::get).toList();
    }

}
