package org.jxch.capital.learning.train.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.domain.dto.KNode;
import org.jxch.capital.domain.dto.KNodeParam;
import org.jxch.capital.domain.dto.KNodeTrain;
import org.jxch.capital.learning.train.TrainDataSignalFilterPreprocessor;
import org.jxch.capital.support.ServiceWrapper;
import org.jxch.capital.utils.AppContextHolder;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Primary
@Service
@RequiredArgsConstructor
public class AutoTrainDataSignalFilterPreprocessor implements TrainDataSignalFilterPreprocessor {

    public List<TrainDataSignalFilterPreprocessor> allPreprocessor() {
        return AppContextHolder.allService(TrainDataSignalFilterPreprocessor.class);
    }

    public List<ServiceWrapper> allPreprocessorWrappers() {
        return allPreprocessor().stream().map(TrainDataSignalFilterPreprocessor::getDefaultServiceWrapper).toList();
    }

    public KNodeParam kNodeParamPreprocess(KNodeParam kNodeParam, @NotNull List<ServiceWrapper> serviceWrappers) {
        for (ServiceWrapper serviceWrapper : serviceWrappers) {
            kNodeParam = kNodeParamPreprocess(kNodeParam, serviceWrapper);
        }
        return kNodeParam;
    }

    @Override
    public KNodeParam kNodeParamPreprocess(KNodeParam kNodeParam, ServiceWrapper serviceWrapper) {
        for (TrainDataSignalFilterPreprocessor preprocessor : allPreprocessor()) {
            if (support(serviceWrapper)) {
                kNodeParam = preprocessor.kNodeParamPreprocess(kNodeParam, serviceWrapper);
            }
        }
        return kNodeParam;
    }

    public List<KNode> kNodesPostProcess(List<KNode> kNodes, @NotNull List<ServiceWrapper> serviceWrappers) {
        for (ServiceWrapper serviceWrapper : serviceWrappers) {
            kNodes = kNodesPostProcess(kNodes, serviceWrapper);
        }
        return kNodes;
    }

    @Override
    public List<KNode> kNodesPostProcess(List<KNode> kNodes, ServiceWrapper serviceWrapper) {
        for (TrainDataSignalFilterPreprocessor preprocessor : allPreprocessor()) {
            if (support(serviceWrapper)) {
                kNodes = preprocessor.kNodesPostProcess(kNodes, serviceWrapper);
            }
        }
        return kNodes;
    }

    public List<KNodeTrain> kNodeTrainsPostProcess(List<KNodeTrain> kNodeTrains, @NotNull List<ServiceWrapper> serviceWrappers) {
        for (ServiceWrapper serviceWrapper : serviceWrappers) {
            kNodeTrains = kNodeTrainsPostProcess(kNodeTrains, serviceWrapper);
        }
        return kNodeTrains;
    }

    @Override
    public List<KNodeTrain> kNodeTrainsPostProcess(List<KNodeTrain> kNodeTrains, ServiceWrapper serviceWrapper) {
        for (TrainDataSignalFilterPreprocessor preprocessor : allPreprocessor()) {
            if (support(serviceWrapper)) {
                kNodeTrains = preprocessor.kNodeTrainsPostProcess(kNodeTrains, serviceWrapper);
            }
        }
        return kNodeTrains;
    }

    @Override
    public boolean support(@NotNull ServiceWrapper serviceWrapper) {
        return false;
    }

}
