package org.jxch.capital.learning.old.train.data.impl;

import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.domain.dto.*;
import org.jxch.capital.learning.old.train.param.TrainDataParam;
import org.jxch.capital.learning.old.train.param.TrainDataRes;
import org.jxch.capital.learning.old.train.param.dto.TrainIndicesDataParam;
import org.jxch.capital.learning.old.train.balancer.AutoTrainDataSignalBalancePreProcessor;
import org.jxch.capital.learning.old.train.booster.TrainDataSignalBooster;
import org.jxch.capital.learning.old.train.booster.TrainDataSignalBoosters;
import org.jxch.capital.learning.old.train.config.TrainConfigService;
import org.jxch.capital.learning.old.train.data.TrainIndicesDataService;
import org.jxch.capital.learning.old.train.filter.AutoTrainDataSignalFilterPreprocessor;
import org.jxch.capital.learning.old.train.param.PredictionDataOneStockParam;
import org.jxch.capital.learning.old.train.param.PredictionDataOneStockRes;
import org.jxch.capital.learning.old.train.param.dto.TrainIndicesDataOneStockRes;
import org.jxch.capital.learning.old.train.scrubber.AutoTrainDataFeaturesScrubberProcessor;
import org.jxch.capital.server.IndicesCombinationService;
import org.jxch.capital.server.KNodeService;
import org.jxch.capital.stock.StockService;
import org.jxch.capital.support.ServiceWrapper;
import org.jxch.capital.support.ServiceWrapperSupports;
import org.jxch.capital.utils.ServiceU;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainIndicesDataServiceImpl implements TrainIndicesDataService {
    private final AutoTrainDataSignalFilterPreprocessor autoTrainDataSignalFilterPreprocessor;
    private final AutoTrainDataSignalBalancePreProcessor autoTrainDataSignalBalancePreProcessor;
    private final AutoTrainDataFeaturesScrubberProcessor autoTrainDataFeaturesScrubberProcessor;
    private final IndicesCombinationService indicesCombinationService;
    private final TrainConfigService trainConfigService;
    private final KNodeService kNodeService;
    private final StockService stockService;

    @Override
    public TrainDataRes trainData(TrainDataParam param) {
        TrainIndicesDataParam trainIndicesDataParam = (TrainIndicesDataParam) param;
        KNodeParam kNodeParam = trainIndicesDataParam.getKNodeParam();
        kNodeParam.setSize(kNodeParam.getSize() + kNodeParam.getFutureNum());
        List<String> indicatorNames = new ArrayList<>();
        if (kNodeParam.hasIndicesComId()) {
            List<IndicatorWrapper> indicatorWrapper = indicesCombinationService.getIndicatorWrapper(kNodeParam.getIndicesComId());
            kNodeParam.addIndicators(indicatorWrapper);
            indicatorNames.addAll(kNodeParam.getIndicatorNames());
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
        kNodeTrains = TrainDataSignalBoosters.booster(kNodeTrains, trainIndicesDataParam.getBoosterWrappers());

        KNodeTrains theKNodeTrains = new KNodeTrains(kNodeTrains, indicatorNames, trainIndicesDataParam.getSimplify() && ServiceU.isExternalService(), trainIndicesDataParam.getTranspose());
        theKNodeTrains = autoTrainDataFeaturesScrubberProcessor.featuresPostProcessor(theKNodeTrains, trainIndicesDataParam.getScrubberWrappers());
        return new TrainIndicesDataOneStockRes(theKNodeTrains);
    }

    @Override
    public PredictionDataOneStockRes predictionOneStockData(@NotNull PredictionDataOneStockParam param, boolean offset) {
        TrainConfigDto config = trainConfigService.findById(param.getTrainConfigId());
        TrainIndicesDataParam indicesDataParam = getParam(config.getParams(), TrainIndicesDataParam.class);

        KNodeParam kNodeParam = indicesDataParam.getKNodeParam().setCode(param.getCode());
        List<String> indicatorNames = new ArrayList<>();
        if (kNodeParam.hasIndicesComId()) {
            kNodeParam.addIndicators(indicesCombinationService.getIndicatorWrapper(kNodeParam.getIndicesComId()));
            indicatorNames.addAll(kNodeParam.getIndicatorNames());
        }

        List<KNode> kNodes;
        Date startDate = param.getStart();
        if (offset) {
            startDate = stockService.getStartOffsetDay(kNodeParam.getSize() + kNodeParam.getMaxLength(), startDate, kNodeParam.getCode());
            kNodes = kNodeService.kNodes(kNodeParam, startDate, param.getEnd())
                    .stream().filter(kNode -> kNode.getLastKLine().getDate().getTime() >= param.getStart().getTime()).toList();
        } else {
            kNodes = kNodeService.kNodes(kNodeParam, startDate, param.getEnd());
        }

        List<KNodeTrain> kNodeTrains = kNodes.stream().map(kNode -> KNodeTrain.builder().code(kNode.getCode()).kNode(kNode).futureNum(0).build()).toList();
        KNodeTrains theKNodeTrains = new KNodeTrains(kNodeTrains, indicatorNames, indicesDataParam.getSimplify() && ServiceU.isExternalService(), indicesDataParam.getTranspose());
        return new TrainIndicesDataOneStockRes(theKNodeTrains);
    }

    @Override
    public String name() {
        return "指标组合特征数据";
    }

    @Override
    public TrainDataParam getDefaultParam() {
        return new TrainIndicesDataParam()
                .setFilterWrappers(autoTrainDataSignalFilterPreprocessor.allPreprocessorWrappers())
                .setBalancerWrappers(autoTrainDataSignalBalancePreProcessor.allServiceWrapper())
                .setScrubberWrappers(autoTrainDataFeaturesScrubberProcessor.allServiceWrapper())
                .setBoosterWrappers(ServiceWrapperSupports.allServiceWrapper(TrainDataSignalBooster.class));
    }

    @Override
    public TrainDataParam getParam(String json) {
        return JSONObject.parseObject(json, TrainIndicesDataParam.class);
    }

}
