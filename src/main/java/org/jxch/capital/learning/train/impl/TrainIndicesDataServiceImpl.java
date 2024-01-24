package org.jxch.capital.learning.train.impl;

import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.*;
import org.jxch.capital.learning.train.TrainDataParam;
import org.jxch.capital.learning.train.TrainDataRes;
import org.jxch.capital.learning.train.TrainIndicesDataService;
import org.jxch.capital.learning.train.dto.TrainIndicesDataParam;
import org.jxch.capital.learning.train.dto.TrainIndicesDataRes;
import org.jxch.capital.server.IndicesCombinationService;
import org.jxch.capital.server.KNodeService;
import org.jxch.capital.support.ServiceWrapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainIndicesDataServiceImpl implements TrainIndicesDataService {
    private final AutoTrainDataSignalFilterPreprocessor autoTrainDataSignalFilterPreprocessor;
    private final AutoTrainDataSignalBalancePreProcessor autoTrainDataSignalBalancePreProcessor;
    private final IndicesCombinationService indicesCombinationService;
    private final KNodeService kNodeService;

    private KNodeTrains setNullIfSimplify(KNodeTrains kNodeTrains, boolean simplify) {
        if (simplify) {
            kNodeTrains.setKNodes(null);
            kNodeTrains.setFeatures(null);
            kNodeTrains.setSignals3(null);
        }
        return kNodeTrains;
    }

    @Override
    public TrainDataRes trainData(TrainDataParam param) {
        TrainIndicesDataParam trainIndicesDataParam = (TrainIndicesDataParam) param;
        KNodeParam kNodeParam = trainIndicesDataParam.getKNodeParam();
        kNodeParam.setSize(kNodeParam.getSize() + kNodeParam.getFutureNum());
        if (kNodeParam.hasIndicesComId()) {
            List<IndicatorWrapper> indicatorWrapper = indicesCombinationService.getIndicatorWrapper(kNodeParam.getIndicesComId());
            kNodeParam.addIndicators(indicatorWrapper);
        }

        List<ServiceWrapper> filterWrappers = trainIndicesDataParam.getFilterWrappers();
        kNodeParam = autoTrainDataSignalFilterPreprocessor.kNodeParamPreprocess(kNodeParam, filterWrappers);
        List<KNode> kNodes = kNodeService.comparison(kNodeParam);
        kNodes = autoTrainDataSignalFilterPreprocessor.kNodesPostProcess(kNodes, filterWrappers);

        KNodeParam finalKNodeParam = kNodeParam;
        List<KNodeTrain> kNodeTrains = kNodes.stream().map(k ->
                KNodeTrain.builder().code(k.getCode()).kNode(k).futureNum(finalKNodeParam.getFutureNum()).build()).toList();
        kNodeTrains = autoTrainDataSignalFilterPreprocessor.kNodeTrainsPostProcess(kNodeTrains, filterWrappers);
        kNodeTrains = autoTrainDataSignalBalancePreProcessor.kNodeTrainsPostProcess(kNodeTrains, trainIndicesDataParam.getBalancerWrappers());

        KNodeTrains theKNodeTrains = KNodeTrains.builder().kNodes(kNodeTrains).build().feature(kNodeParam.getIndicatorNames());
        return TrainIndicesDataRes.builder().kNodeTrains(setNullIfSimplify(theKNodeTrains, trainIndicesDataParam.getSimplify())).build();
    }

    @Override
    public TrainDataRes predictionData(TrainDataParam param) {
        TrainIndicesDataParam indicesDataParam = (TrainIndicesDataParam) param;
        KNodeParam kNodeParam = indicesDataParam.getKNodeParam();
        kNodeParam.setSize(kNodeParam.getSize() + kNodeParam.getFutureNum());
        if (kNodeParam.hasIndicesComId()) {
            kNodeParam.addIndicators(indicesCombinationService.getIndicatorWrapper(kNodeParam.getIndicesComId()));
        }

        List<KNode> kNodes = new ArrayList<>();
        if (indicesDataParam.getOnlyPredictionData()) {
            kNodes.add(kNodeService.kNode(kNodeParam));
        } else {
            kNodes = kNodeService.kNodes(kNodeParam, indicesDataParam.getPredictionStartDate(), indicesDataParam.getPredictionEndDate());
        }

        List<KNodeTrain> kNodeTrains = kNodes.stream().map(kNode -> KNodeTrain.builder().code(kNode.getCode()).kNode(kNode).futureNum(0).build()).toList();

        return TrainIndicesDataRes.builder().kNodeTrains(setNullIfSimplify(KNodeTrains.builder().kNodes(kNodeTrains).build(), indicesDataParam.getSimplify())).build();
    }

    @Override
    public String name() {
        return "指标组合特征数据";
    }

    @Override
    public TrainDataParam getDefaultParam() {
        return new TrainIndicesDataParam()
                .setFilterWrappers(autoTrainDataSignalFilterPreprocessor.allPreprocessorWrappers())
                .setBalancerWrappers(autoTrainDataSignalBalancePreProcessor.allServiceWrapper());
    }

    @Override
    public TrainDataParam getParam(String json) {
        return JSONObject.parseObject(json, TrainIndicesDataParam.class);
    }

}
