package org.jxch.capital.support.policy;

import org.jetbrains.annotations.NotNull;
import org.jxch.capital.utils.AppContextHolder;

public class AutoPolicymakerChain {

    public static <T extends PolicymakerChainParam> T chain(@NotNull T chainParam, Class<? extends PolicymakerChain> policymaker) {
        @SuppressWarnings("unchecked")
        Class<T> chainParamClazz = (Class<T>) chainParam.getClass();
        for (PolicymakerChain policymakerChain : AppContextHolder.allServiceSorted(policymaker)) {
            chainParam = chainParamClazz.cast(policymakerChain.doChain(chainParam));
        }
        return chainParam;
    }

}
