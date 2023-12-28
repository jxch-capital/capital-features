package org.jxch.capital.server;

import org.jxch.capital.domain.dto.KNode;

import java.util.List;

public interface KNodeService {

    KNode current(String code, int size, IntervalEnum intervalEnum);

    List<KNode> comparison(long stockPoolId, int size);

}
