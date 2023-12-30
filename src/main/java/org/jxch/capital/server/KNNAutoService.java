package org.jxch.capital.server;

import org.jxch.capital.domain.dto.KNeighbor;

import java.util.List;

public interface KNNAutoService {

    List<KNeighbor> search(String name, String code, long stockPoolId, int size);

}
