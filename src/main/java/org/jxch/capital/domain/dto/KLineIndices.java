package org.jxch.capital.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class KLineIndices extends KLine {

    private Map<String, Double> indices = Collections.synchronizedMap(new TreeMap<>());

    public void setIndex(String name, Double value) {
        indices.put(name, value);
    }

}
