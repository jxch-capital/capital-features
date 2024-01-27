package org.jxch.capital.learning.train.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.learning.train.TrainDataFeaturesScrubberProcessor;
import org.jxch.capital.support.ServiceWrapper;
import org.jxch.capital.utils.MathU;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainDataFeaturesDefaultScrubberProcessor implements TrainDataFeaturesScrubberProcessor {

    @Override
    public boolean support(double[][][] features, @NotNull ServiceWrapper serviceWrapper) {
        return Objects.equals(serviceWrapper.getName(), name()) && Objects.nonNull(features) && MathU.hasInvalid3(features);
    }

    @Override
    public double[][][] featuresPostProcessor(double[][][] features, ServiceWrapper serviceWrapper) {
        return Arrays.stream(features).filter(line -> !MathU.hasInvalid2(line)).toArray(double[][][]::new);
    }

    @Override
    public String name() {
        return "默认特征数据清洗器";
    }

}
