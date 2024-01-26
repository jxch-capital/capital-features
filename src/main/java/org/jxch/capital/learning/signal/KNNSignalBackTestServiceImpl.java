package org.jxch.capital.learning.signal;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KLineSignal;
import org.jxch.capital.domain.dto.KNNParam;
import org.jxch.capital.domain.dto.KNode;
import org.jxch.capital.event.ServerJobProgressEvent;
import org.jxch.capital.event.dto.ServerJobProgressEventDto;
import org.jxch.capital.learning.knn.KNNService;
import org.jxch.capital.learning.knn.KNNs;
import org.jxch.capital.learning.signal.dto.SignalBackTestKNNParam;
import org.jxch.capital.server.KNodeAnalyzeService;
import org.jxch.capital.server.KNodeService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KNNSignalBackTestServiceImpl implements KNNSignalBackTestService {
    private final KNodeService kNodeService;
    private final KNodeAnalyzeService kNodeAnalyzeService;
    private final ApplicationEventPublisher publisher;

    @Override
    public Integer signal(KNode kNode, SignalBackTestKNNParam param) {
        // todo 删除接口的这个方法，或者用 ThreadLocal
        throw new UnsupportedOperationException();
    }

    @Override
    public List<KLineSignal> backTestByCode(@NonNull SignalBackTestKNNParam param) {
        TimeInterval timer = DateUtil.timer();

        param.getKnnParam().getKNodeParam().setCode(param.getCode());
        KNNParam knnParam = param.getKnnParam();
        int sourceSize = knnParam.getKNodeParam().getSize();
        int futureSize = knnParam.getKNodeParam().getSize() + param.getFutureNum();
        KNNService distanceService = KNNs.getKNNService(knnParam.getDistanceName());

        List<KNode> futureKNodes = kNodeService.comparison(knnParam.getKNodeParam().setSize(futureSize));
        List<KNode> codeFutureKNodes = kNodeService.kNodes(param.getKnnParam().getKNodeParam().setSize(futureSize), param.getStart(), param.getEnd());

        List<KNode> proc = Collections.synchronizedList(new ArrayList<>(codeFutureKNodes));

        List<KLineSignal> kLineSignals = codeFutureKNodes.parallelStream()
                .map(futureKNode -> {
                    KNode kNode = futureKNode.subtractLast(param.getFutureNum());
                    proc.remove(futureKNode);
                    log.debug("{} 进度：{} / {}", param.getCode(), codeFutureKNodes.size() - proc.size(), codeFutureKNodes.size());
                    publisher.publishEvent(new ServerJobProgressEvent(ServerJobProgressEventDto.builder()
                            .title("KNN信号回测")
                            .source(super.getClass().getSimpleName())
                            .progress((codeFutureKNodes.size() - proc.size()) / (double) codeFutureKNodes.size())
                            .detail(param.getCode() + " 进度：" + (codeFutureKNodes.size() - proc.size()) + " / " + codeFutureKNodes.size())
                            .build()));

                    return KLineSignal.builder()
                            .kLine(kNode.getLastKLine())
                            .signal((double) kNodeAnalyzeService.statisticsKNNHasFuture(
                                            distanceService.searchHasFuture(futureKNode, futureKNodes, param.getKnnParam().getNeighborSize(), param.getFutureNum())
                                            , param.getFutureNum())
                                    .getFutureSignal())
                            .tureSignal(futureKNode.getLastKLine().getClose() - kNode.getLastKLine().getClose())
                            .code(param.getCode())
                            .build();
                })
                .sorted(Comparator.comparing(kLineSignal -> kLineSignal.getKLine().getDate()))
                .toList();


        KNode lastKNode = codeFutureKNodes.get(codeFutureKNodes.size() - 1);
        List<KNode> kNodes = lastKNode.sliceOut0(sourceSize);

        List<KLineSignal> kLineSignalsAppend = kNodes.parallelStream()
                .map(kNode -> KLineSignal.builder()
                        .kLine(kNode.getLastKLine())
                        .signal((double) kNodeAnalyzeService.statisticsKNNHasFuture(
                                distanceService.searchHasFutureNodes(kNode, futureKNodes, param.getKnnParam().getNeighborSize(), param.getFutureNum()),
                                param.getFutureNum()).getFutureSignal())
                        .code(param.getCode())
                        .build())
                .sorted(Comparator.comparing(kLineSignal -> kLineSignal.getKLine().getDate()))
                .toList();

        ArrayList<KLineSignal> allKLineSignals = new ArrayList<>(kLineSignals);
        allKLineSignals.addAll(kLineSignalsAppend);

        log.debug("回测处理时间：{}s", timer.intervalSecond());
        knnParam.getKNodeParam().setSize(sourceSize);
        return allKLineSignals;
    }

}
