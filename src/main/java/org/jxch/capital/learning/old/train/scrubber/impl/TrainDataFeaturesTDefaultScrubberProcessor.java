package org.jxch.capital.learning.old.train.scrubber.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.domain.dto.KNodeTrains;
import org.jxch.capital.learning.old.train.scrubber.TrainDataFeaturesScrubberProcessor;
import org.jxch.capital.support.ServiceWrapper;
import org.jxch.capital.utils.MathU;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainDataFeaturesTDefaultScrubberProcessor implements TrainDataFeaturesScrubberProcessor {

    @Override
    public boolean support(KNodeTrains kNodeTrains, @NotNull ServiceWrapper serviceWrapper) {
        return Objects.equals(serviceWrapper.getName(), name()) &&
                (
                        (Objects.nonNull(kNodeTrains.getFeatures()) && MathU.hasInvalid3(kNodeTrains.getFeatures()))
                );
    }

    @Override
    public KNodeTrains featuresPostProcessor(@NotNull KNodeTrains kNodeTrains, ServiceWrapper serviceWrapper) {
        List<double[][]> x = new ArrayList<>();
        List<Integer> yUp = new ArrayList<>();
        List<Integer> yDown = new ArrayList<>();

        double[][][] featuresT = kNodeTrains.getFeatures();
        int[] upSignals = kNodeTrains.getUpSignals();
        int[] downSignals = kNodeTrains.getDownSignals();

        for (int index = 0; index < featuresT.length; index++) {
            if (!MathU.hasInvalid2(featuresT[index])) {
                x.add(featuresT[index]);
                yUp.add(upSignals[index]);
                yDown.add(downSignals[index]);
            }
        }

        return kNodeTrains.setFeatures(x.toArray(new double[0][][]))
                .setUpSignals(yUp.stream().mapToInt(i -> i).toArray())
                .setDownSignals(yDown.stream().mapToInt(i -> i).toArray());
    }

    @Override
    public String name() {
        return "默认特征数据清洗器";
    }
}
