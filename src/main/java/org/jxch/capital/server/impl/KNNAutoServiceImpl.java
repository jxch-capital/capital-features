package org.jxch.capital.server.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KNeighbor;
import org.jxch.capital.server.KNNAutoService;
import org.jxch.capital.server.KNNService;
import org.jxch.capital.server.KNNs;
import org.jxch.capital.server.KNodeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KNNAutoServiceImpl implements KNNAutoService {
    private final KNodeService kNodeService;

    @Override
    public List<KNeighbor> search(String name, String code, long stockPoolId, int size) {
        KNNService distanceService = KNNs.getDistanceService(name);
        return distanceService.search(distanceService.getDefaultKNodeParam().setCode(code).setStockPoolId(stockPoolId), kNodeService, size);
    }

}
