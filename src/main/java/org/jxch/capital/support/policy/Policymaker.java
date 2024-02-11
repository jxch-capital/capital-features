package org.jxch.capital.support.policy;

import org.springframework.core.Ordered;

public interface Policymaker<S extends PolicymakerSupportParam, P extends PolicymakerApplyParam, R extends PolicymakerApplyRes> extends Ordered {

    boolean support(S supportParam);

    R apply(P applyParam);

    default int getOrder() {
        return 0;
    }

}
