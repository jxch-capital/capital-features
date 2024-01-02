package org.jxch.capital.server.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.*;
import org.jxch.capital.server.IndicesCombinationService;
import org.jxch.capital.server.KNodeService;
import org.jxch.capital.server.TrainDataService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainDataServiceImpl implements TrainDataService {
    private final IndicesCombinationService indicesCombinationService;
    private final KNodeService kNodeService;

    @Override
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
    public KNodeTrains predictionData(@NonNull List<KNodeParam> kNodeParams) {
        List<KNode> kNodes = kNodeParams.stream().map(kNodeParam -> {
            if (kNodeParam.hasIndicesComId()) {
                kNodeParam.addIndicators(indicesCombinationService.getIndicatorWrapper(kNodeParam.getIndicesComId()));
            }
            return kNodeService.kNode(kNodeParam);
        }).toList();

        List<KNodeTrain> kNodeTrains = kNodes.stream().map(k ->
                KNodeTrain.builder().code(k.getCode()).kNode(k).futureNum(0).build()).toList();
        return KNodeTrains.builder().kNodes(kNodeTrains).build();
    }

}
