package org.jxch.capital.learning.old.train;

import org.jxch.capital.support.ServiceWrapperGetter;
import org.jxch.capital.support.ServiceWrapperSupport;
import org.jxch.capital.support.policy.PolicymakerChain;
import org.jxch.capital.support.policy.PolicymakerChainParam;

public interface ServiceWrapperPolicymakerChain extends PolicymakerChain, ServiceWrapperSupport {

    @Override
    default boolean support(PolicymakerChainParam supportParam) {
        return supportParam instanceof ServiceWrapperGetter &&
                support(((ServiceWrapperGetter) supportParam).getServiceWrapper());
    }

}
