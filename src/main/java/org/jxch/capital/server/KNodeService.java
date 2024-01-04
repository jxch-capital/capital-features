package org.jxch.capital.server;

import org.jxch.capital.domain.dto.KLine;
import org.jxch.capital.domain.dto.KNode;
import org.jxch.capital.domain.dto.KNodeParam;

import java.util.Date;
import java.util.List;

public interface KNodeService {

    List<KNode> kNodes(KNodeParam kNodeParam, Date start, Date end);

    List<KNode> kNodes(KNodeParam kNodeParam, List<KLine> kLines);

    KNode kNode(KNodeParam kNodeParam);

    KNode current(KNodeParam kNodeParam);

    List<KNode> comparison(KNodeParam kNodeParam);

}
