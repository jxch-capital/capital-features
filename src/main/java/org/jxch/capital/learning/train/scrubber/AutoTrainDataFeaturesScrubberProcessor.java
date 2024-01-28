package org.jxch.capital.learning.train.scrubber;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.domain.dto.KNodeTrains;
import org.jxch.capital.learning.train.scrubber.TrainDataFeaturesScrubberProcessor;
import org.jxch.capital.support.AutoServiceWrapperSupport;
import org.jxch.capital.support.ServiceWrapper;
import org.jxch.capital.support.ServiceWrapperSupport;
import org.jxch.capital.utils.AppContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutoTrainDataFeaturesScrubberProcessor implements TrainDataFeaturesScrubberProcessor, AutoServiceWrapperSupport {
    @Override
    public List<ServiceWrapper> allServiceWrapper() {
        return AppContextHolder.allServiceExcept(TrainDataFeaturesScrubberProcessor.class, getClass()).stream()
                .map(ServiceWrapperSupport::getDefaultServiceWrapper).toList();
    }

    public KNodeTrains featuresPostProcessor(@NotNull KNodeTrains kNodeTrains, @NotNull List<ServiceWrapper> serviceWrappers) {
        for (ServiceWrapper serviceWrapper : serviceWrappers) {
            kNodeTrains = featuresPostProcessor(kNodeTrains, serviceWrapper);
        }
        return kNodeTrains;
    }

    @Override
    public boolean support(KNodeTrains kNodeTrains, ServiceWrapper serviceWrapper) {
        return false;
    }

    @Override
    public KNodeTrains featuresPostProcessor(@NotNull KNodeTrains kNodeTrains, ServiceWrapper serviceWrapper) {
        for (TrainDataFeaturesScrubberProcessor processor : AppContextHolder.allServiceExcept(TrainDataFeaturesScrubberProcessor.class, getClass())) {
            if (processor.support(kNodeTrains, serviceWrapper)) {
                kNodeTrains = processor.featuresPostProcessor(kNodeTrains, serviceWrapper);
            }
        }
        return kNodeTrains;
    }
}
