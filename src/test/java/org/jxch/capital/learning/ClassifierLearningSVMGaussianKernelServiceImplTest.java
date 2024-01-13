package org.jxch.capital.learning;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.jxch.capital.domain.dto.KLineSignal;
import org.jxch.capital.domain.dto.KNode;
import org.jxch.capital.domain.dto.KNodeParam;
import org.jxch.capital.learning.classifier.config.ClassifierLearningConfig;
import org.jxch.capital.learning.classifier.model.ClassifierLearningSVMGaussianKernelServiceImpl;
import org.jxch.capital.learning.classifier.ClassifierLearnings;
import org.jxch.capital.learning.classifier.dto.ClassifierLearningParam;
import org.jxch.capital.learning.classifier.dto.ClassifierLearningRes;
import org.jxch.capital.stock.IntervalEnum;
import org.jxch.capital.server.impl.KNodeServiceImpl;
import org.jxch.capital.utils.KLineSignals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import smile.classification.Classifier;

import java.util.Calendar;
import java.util.List;

@Slf4j
@SpringBootTest
class ClassifierLearningSVMGaussianKernelServiceImplTest {
    @Autowired
    private ClassifierLearningSVMGaussianKernelServiceImpl kernelService;
    @Autowired
    private KNodeServiceImpl kNodeService;
    @Autowired
    private ClassifierLearningConfig classifierLearningConfig;

    @Test
    void predict() {
        KNodeParam kNodeParam = KNodeParam.builder()
                .code("AAPL")
                .stockPoolId(539952)
                .maxLength(20)
                .size(20)
                .intervalEnum(IntervalEnum.DAY_1)
                .build();

        List<KNode> kNodesT = kNodeService.comparison(kNodeParam);
        List<KNode> kNodesP = kNodeService.kNodes(kNodeParam, DateUtil.offset(Calendar.getInstance().getTime(), DateField.YEAR, -2), Calendar.getInstance().getTime());
        List<KLineSignal> kLineSignals = KLineSignals.setTrueSignalHasLastNull(kNodesP, 6, 20);


        ClassifierLearningParam param = kernelService.defaultParam()
                .setKNodesT(kNodesT)
                .setKNodesP(kNodesP)
                .setFutureNum(6)
                .setSize(20)
                .setAllDataByKLineH();

        ClassifierLearningRes predict = kernelService.fitAndPredict(param);

        for (int i = 0; i < kLineSignals.size(); i++) {
            kLineSignals.get(i).setSignal(predict.getSignal(i));
        }

        log.info(JSONObject.toJSONString(predict.getYP()));

        kernelService.save(param.setModelName(param.getModelName() + "TEST"));
    }


    @Test
    void load() {
        List<String> allLocalModel = ClassifierLearnings.allLocalModel(classifierLearningConfig.getModelPath());
        log.info(JSONObject.toJSONString(allLocalModel));

        Classifier<double[]> load = ClassifierLearnings.load(classifierLearningConfig.getModelPath() + allLocalModel.get(0));




    }

}