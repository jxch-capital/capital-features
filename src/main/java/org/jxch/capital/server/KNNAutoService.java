package org.jxch.capital.server;

import org.jxch.capital.domain.dto.KNeighbor;
import org.jxch.capital.domain.dto.KNodeParam;

import java.util.List;

public interface KNNAutoService {

    List<KNeighbor> search(String name, KNodeParam kNodeParam, int size);

}
