package org.jxch.capital.server.impl;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KLineFeatures;
import org.jxch.capital.server.DistanceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Service
public class LorentzianDistanceServiceImpl implements DistanceService<KLineFeatures> {

    private double[] computeFeatureAverages(@NonNull List<KLineFeatures> featuresList) {
        return IntStream.range(0, featuresList.get(0).getFeaturesNum())
                .mapToDouble(i -> featuresList.stream()
                        .mapToDouble(k -> k.get(i))
                        .average()
                        .orElseThrow(IllegalStateException::new))
                .toArray();
    }

    @Override
    public double distance(@NonNull List<KLineFeatures> a, @NonNull List<KLineFeatures> b) {
        int featuresNum = a.get(0).getFeaturesNum();
        // 提前计算两个列表中所有特征的平均值
        double[] aAverages = computeFeatureAverages(a);
        double[] bAverages = computeFeatureAverages(b);

        if (aAverages.length != bAverages.length) {
            throw new IllegalArgumentException("特征数量必须相同");
        }

        // 使用预先计算的平均值来计算距离
        return IntStream.range(0, featuresNum)
                .mapToDouble(i -> Math.log(1 + Math.abs(aAverages[i] - bAverages[i])))
                .sum();
    }

}
