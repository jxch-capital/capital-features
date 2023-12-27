package org.jxch.capital.domain.dto;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class KLineFeatures extends KLine {
    private List<Double> features = new ArrayList<>();

    @Getter
    private boolean hasNull = false;

    public int getFeaturesNum(){
        return features.size();
    }

    public void addFeature(Double f) {
        features.add(f);
        if (Objects.isNull(f)) {
            hasNull = true;
        }
    }

    public List<Double> getFeatures() {
        return new ArrayList<>(features);
    }

    public double get(int index) {
        return features.get(index);
    }

}
