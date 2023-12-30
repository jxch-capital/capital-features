package org.jxch.capital.server.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KNeighbor;
import org.jxch.capital.domain.dto.KNodeParam;
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
    public List<KNeighbor> search(String name, @NonNull KNodeParam kNodeParam, int size) {
        KNNService distanceService = KNNs.getDistanceService(name);
        return distanceService.search(kNodeParam.add(distanceService.getDefaultKNodeParam()), kNodeService, size);
    }

}
