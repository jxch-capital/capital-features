package org.jxch.capital.learning.train;

import org.jetbrains.annotations.NotNull;
import org.jxch.capital.domain.dto.KNodeTrains;
import org.jxch.capital.support.ServiceWrapper;
import org.jxch.capital.support.ServiceWrapperSupport;

public interface TrainDataFeaturesScrubberProcessor extends ServiceWrapperSupport {

    boolean support(double[][][] features, ServiceWrapper serviceWrapper);

    double[][][] featuresPostProcessor(double[][][] features, ServiceWrapper serviceWrapper);

    default KNodeTrains featuresPostProcessor(@NotNull KNodeTrains kNodeTrains, ServiceWrapper serviceWrapper) {
        return kNodeTrains.setFeatures(featuresPostProcessor(kNodeTrains.getFeatures(), serviceWrapper))
                .setFeaturesT(featuresPostProcessor(kNodeTrains.getFeaturesT(), serviceWrapper));
    }

}
