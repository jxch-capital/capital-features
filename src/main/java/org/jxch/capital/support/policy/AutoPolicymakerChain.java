package org.jxch.capital.support.policy;

import org.jxch.capital.utils.AppContextHolder;

public class AutoPolicymakerChain {

    public static PolicymakerChainParam chain(PolicymakerChainParam chainParam, Class<? extends PolicymakerChain> policymaker) {
        for (PolicymakerChain policymakerChain : AppContextHolder.allServiceSorted(policymaker)) {
            chainParam = policymakerChain.doChain(chainParam);
        }
        return chainParam;
    }

}
