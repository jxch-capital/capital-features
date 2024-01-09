package org.jxch.capital.learning.classifier.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jxch.capital.domain.dto.KNode;
import org.jxch.capital.utils.KNodes;

import java.util.List;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class ClassifierFitParam {
    @Builder.Default
    private double[][] xT = null;
    @Builder.Default
    private int[] yT = null;

    private List<KNode> kNodesT;
    private int futureNum;

    public ClassifierFitParam setTDataByKLineH() {
        return this.setYT(KNodes.futures(this.getKNodesT(), this.getFutureNum()))
                .setXT(KNodes.normalizedKArrH(KNodes.subtractLast(this.getKNodesT(), this.getFutureNum())));
    }

    public ClassifierFitParam setTDataByKLineV() {
        return this.setYT(KNodes.futures(this.getKNodesT(), this.getFutureNum()))
                .setXT(KNodes.normalizedKArrV(KNodes.subtractLast(this.getKNodesT(), this.getFutureNum())));
    }

    public ClassifierFitParam setTDataByIndicesH() {
        return this.setYT(KNodes.futures(this.getKNodesT(), this.getFutureNum()))
                .setXT(KNodes.normalizedIndicesArrH(KNodes.subtractLast(this.getKNodesT(), this.getFutureNum())));
    }

    public ClassifierFitParam setTDataByIndicesV() {
        return this.setYT(KNodes.futures(this.getKNodesT(), this.getFutureNum()))
                .setXT(KNodes.normalizedIndicesArrV(KNodes.subtractLast(this.getKNodesT(), this.getFutureNum())));
    }

}
