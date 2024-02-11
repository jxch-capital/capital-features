package org.jxch.capital.support.policy;

public interface PolicymakerChain extends Policymaker<PolicymakerChainParam, PolicymakerChainParam, PolicymakerChainParam> {

    @Override
    boolean support(PolicymakerChainParam supportParam);

    @Override
    PolicymakerChainParam apply(PolicymakerChainParam applyParam);

    default PolicymakerChainParam doChain(PolicymakerChainParam chainParam) {
        if (support(chainParam)) {
            return apply(chainParam);
        }

        return chainParam;
    }

}
