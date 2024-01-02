package org.jxch.capital.server;

import org.jxch.capital.domain.dto.KNodeParam;
import org.jxch.capital.domain.dto.KNodeTrains;

import java.util.List;

public interface TrainDataService {

    KNodeTrains trainData(KNodeParam kNodeParam);

    KNodeTrains predictionData(List<KNodeParam> kNodeParams);

}
