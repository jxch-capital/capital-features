package org.jxch.capital.learning.old.train.filter.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.learning.old.train.filter.param.KNodeTrainsFilterChainParam;
import org.jxch.capital.learning.old.train.filter.param.KNodeTrainsFilterParam;
import org.jxch.capital.learning.old.train.filter.KNodeTrainsFilter;
import org.jxch.capital.support.policy.PolicymakerChainParam;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdvantageRateFilter implements KNodeTrainsFilter {

    @Override
    public PolicymakerChainParam apply(PolicymakerChainParam applyParam) {
        var chainParam = (KNodeTrainsFilterChainParam) applyParam;
        var filterParam = chainParam.getServiceWrapper().getParamObj(KNodeTrainsFilterParam.class);

        return chainParam.setKNodeTrains(chainParam.getKNodeTrains().stream().filter(kNodeTrain -> {
            
            return true;
        }).toList());
    }

    @Override
    public Object getDefaultParam() {
        return new KNodeTrainsFilterParam();
    }

    @Override
    public String name() {
        return "优势率过滤器";
    }

}
