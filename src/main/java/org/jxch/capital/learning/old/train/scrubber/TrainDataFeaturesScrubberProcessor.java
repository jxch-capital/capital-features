package org.jxch.capital.learning.old.train.scrubber;

import org.jetbrains.annotations.NotNull;
import org.jxch.capital.domain.dto.KNodeTrains;
import org.jxch.capital.support.ServiceWrapper;
import org.jxch.capital.support.ServiceWrapperSupport;

public interface TrainDataFeaturesScrubberProcessor extends TrainDataScrubber, ServiceWrapperSupport {

    boolean support(KNodeTrains kNodeTrains, ServiceWrapper serviceWrapper);

    KNodeTrains featuresPostProcessor(@NotNull KNodeTrains kNodeTrains, ServiceWrapper serviceWrapper);
}