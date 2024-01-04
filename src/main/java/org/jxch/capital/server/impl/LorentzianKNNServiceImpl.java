package org.jxch.capital.server.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.domain.dto.KLineFeatures;
import org.jxch.capital.domain.dto.KLineIndices;
import org.jxch.capital.domain.dto.KNodeParam;
import org.jxch.capital.server.IntervalEnum;
import org.jxch.capital.server.KNNService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class LorentzianKNNServiceImpl implements KNNService {

    private double[] computeFeatureAverages(@NonNull List<KLineFeatures> featuresList) {
        return IntStream.range(0, featuresList.get(0).getFeaturesNum())
                .mapToDouble(i -> featuresList.stream()
                        .mapToDouble(k -> k.get(i))
                        .average()
                        .orElseThrow(IllegalStateException::new))
                .toArray();
    }

    @Override
    public double distance(@NonNull List<KLine> a, @NonNull List<KLine> b) {
        List<KLineFeatures> fa = a.stream().map(kLine -> KLineFeatures.valueOf((KLineIndices) kLine)).toList();
        List<KLineFeatures> fb = b.stream().map(kLine -> KLineFeatures.valueOf((KLineIndices) kLine)).toList();

        int featuresNum = fa.get(0).getFeaturesNum();
        // 提前计算两个列表中所有特征的平均值
        double[] aAverages = computeFeatureAverages(fa);
        double[] bAverages = computeFeatureAverages(fb);

        if (aAverages.length != bAverages.length) {
            throw new IllegalArgumentException("特征数量必须相同");
        }

        // 使用预先计算的平均值来计算距离
        return IntStream.range(0, featuresNum)
                .mapToDouble(i -> Math.log(1 + Math.abs(aAverages[i] - bAverages[i])))
                .sum();
    }

    @Override
    public String getName() {
        return "洛伦兹距离-结构优先";
    }

    @Override
    public KNodeParam getDefaultKNodeParam() {
        return KNodeParam.builder()
                .maxLength(20)
                .size(1)
                .intervalEnum(IntervalEnum.DAY_1)
                .build();
    }


}
