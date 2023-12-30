package org.jxch.capital.server;

import org.jxch.capital.domain.dto.KNode;
import org.jxch.capital.domain.dto.KNodeParam;

import java.util.List;

public interface KNodeService {

    KNode current(KNodeParam kNodeParam);

    List<KNode> comparison(KNodeParam kNodeParam);

}
