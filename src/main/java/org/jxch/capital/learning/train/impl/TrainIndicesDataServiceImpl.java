package org.jxch.capital.learning.train.impl;

import com.alibaba.fastjson2.JSONObject;
import lombok.NonNull;
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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainIndicesDataServiceImpl implements TrainIndicesDataService {
    private final IndicesCombinationService indicesCombinationService;
    private final KNodeService kNodeService;

    public KNodeTrains trainData(@NonNull KNodeParam kNodeParam) {
        if (kNodeParam.hasIndicesComId()) {
            List<IndicatorWrapper> indicatorWrapper = indicesCombinationService.getIndicatorWrapper(kNodeParam.getIndicesComId());
            kNodeParam.addIndicators(indicatorWrapper);
        }
        List<KNode> kNodes = kNodeService.comparison(kNodeParam);
        List<KNodeTrain> kNodeTrains = kNodes.stream().map(k ->
                KNodeTrain.builder().code(k.getCode()).kNode(k).futureNum(kNodeParam.getFutureNum()).build()).toList();
        return KNodeTrains.builder().kNodes(kNodeTrains).build();
    }

    @Override
    public TrainDataRes trainData(TrainDataParam param) {
        return TrainIndicesDataRes.builder().kNodeTrains(trainData(((TrainIndicesDataParam) param).getKNodeParam())).build();
    }

    @Override
    public TrainDataRes predictionData(TrainDataParam param) {
        TrainIndicesDataParam indicesDataParam = (TrainIndicesDataParam) param;
        KNodeParam kNodeParam = indicesDataParam.getKNodeParam();
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
        return TrainIndicesDataRes.builder().kNodeTrains(KNodeTrains.builder().kNodes(kNodeTrains).build()).build();
    }

    @Override
    public String name() {
        return "指标组合特征数据";
    }

    @Override
    public TrainDataParam getDefaultParam() {
        return new TrainIndicesDataParam();
    }

    @Override
    public TrainDataParam getParam(String json) {
        return JSONObject.parseObject(json, TrainIndicesDataParam.class);
    }

}
