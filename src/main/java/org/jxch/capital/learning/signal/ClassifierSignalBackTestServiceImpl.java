package org.jxch.capital.learning.signal;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KLineSignal;
import org.jxch.capital.domain.dto.KNode;
import org.jxch.capital.domain.dto.KNodeParam;
import org.jxch.capital.learning.classifier.ClassifierLearningService;
import org.jxch.capital.learning.classifier.ClassifierLearnings;
import org.jxch.capital.learning.classifier.dto.ClassifierLearningRes;
import org.jxch.capital.learning.signal.dto.SignalBackTestClassifierParam;
import org.jxch.capital.server.impl.KNodeServiceImpl;
import org.jxch.capital.utils.KLineSignals;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassifierSignalBackTestServiceImpl implements ClassifierSignalBackTestService {
    private final KNodeServiceImpl kNodeService;

    @Override
    public List<KLineSignal> backTestByCode(@NonNull SignalBackTestClassifierParam param) {
        int size = param.getKNodeParam().getSize();
        int futureSize = size + param.getFutureNum();
        KNodeParam kNodeParam = param.getKNodeParam().setCode(param.getCode()).setSize(futureSize);

        List<KNode> kNodesT = kNodeService.comparison(kNodeParam);
        List<KNode> kNodesP = kNodeService.kNodes(kNodeParam, param.getStart(), param.getEnd());
        List<KLineSignal> kLineSignals = KLineSignals.setTrueSignalHasLastNull(kNodesP, param.getFutureNum(), size);

        ClassifierLearningService service = ClassifierLearnings.getClassifierLearningService(param.getClassifierName());
        ClassifierLearningRes predict = service.fitAndPredict(
                service.defaultParam()
                        .setKNodesT(kNodesT)
                        .setKNodesP(kNodesP)
                        .setFutureNum(param.getFutureNum())
                        .setSize(size));

        for (int i = 0; i < kLineSignals.size(); i++) {
            kLineSignals.get(i).setSignal(predict.getSignal(i));
        }

        return kLineSignals;
    }

}
