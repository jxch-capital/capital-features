package org.jxch.capital.domain.dto;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class KLineFeatures extends KLine {
    @Builder.Default
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

    public void addFeatures(Collection<Double> fs) {
        features.addAll(fs);
        if (fs.contains(null)) {
            hasNull = true;
        }
    }

    public List<Double> getFeatures() {
        return new ArrayList<>(features);
    }

    public double get(int index) {
        return features.get(index);
    }

    private static KLineFeatures buildKLineFeatures(@NonNull KLineIndices indices) {
        return (KLineFeatures) KLineFeatures.builder().build()
                .setDate(indices.getDate())
                .setClose(indices.getClose())
                .setOpen(indices.getOpen())
                .setHigh(indices.getHigh())
                .setLow(indices.getLow())
                .setVolume(indices.getVolume());
    }

    @NonNull
    public static KLineFeatures valueOf(@NonNull KLineIndices indices) {
        KLineFeatures kLineFeatures = buildKLineFeatures(indices);
        kLineFeatures.addFeatures(indices.getIndices().values());
        return kLineFeatures;
    }

    @NonNull
    public static KLineFeatures valueOf(@NonNull KLineIndices indices, @NonNull List<String> indicesNames) {
        KLineFeatures kLineFeatures = buildKLineFeatures(indices);
        kLineFeatures.addFeatures(indicesNames.stream().map(indices::get).toList());
        return kLineFeatures;
    }

}
