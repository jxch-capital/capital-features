package org.jxch.capital.learning.train.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.domain.dto.KNodeTrain;
import org.jxch.capital.learning.train.TrainDataSignalBalancePreProcessor;
import org.jxch.capital.support.AutoServiceWrapperSupport;
import org.jxch.capital.support.ServiceWrapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Primary
@Service
@RequiredArgsConstructor
public class AutoTrainDataSignalBalancePreProcessor implements TrainDataSignalBalancePreProcessor, AutoServiceWrapperSupport {

    @Override
    public List<ServiceWrapper> allServiceWrapper() {
        return allServiceWrapper(TrainDataSignalBalancePreProcessor.class);
    }

    public List<KNodeTrain> kNodeTrainsPostProcess(List<KNodeTrain> kNodeTrains, @NotNull List<ServiceWrapper> serviceWrappers) {
        for (ServiceWrapper serviceWrapper : serviceWrappers) {
            kNodeTrains = kNodeTrainsPostProcess(kNodeTrains, serviceWrapper);
        }
        return kNodeTrains;
    }

    @Override
    public List<KNodeTrain> kNodeTrainsPostProcess(List<KNodeTrain> kNodeTrains, ServiceWrapper serviceWrapper) {
        for (TrainDataSignalBalancePreProcessor processor : allService(TrainDataSignalBalancePreProcessor.class)) {
            if (processor.support(serviceWrapper)) {
                kNodeTrains = processor.kNodeTrainsPostProcess(kNodeTrains, serviceWrapper);
            }
        }
        return kNodeTrains;
    }

    @Override
    public boolean support(@NotNull ServiceWrapper serviceWrapper) {
        return false;
    }
}
