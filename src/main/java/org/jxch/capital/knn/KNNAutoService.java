package org.jxch.capital.knn;

import lombok.NonNull;
import org.jxch.capital.domain.dto.KNNParam;
import org.jxch.capital.domain.dto.KNeighbor;
import org.jxch.capital.domain.dto.KNode;
import org.jxch.capital.domain.dto.KNodeParam;

import java.util.List;

public interface KNNAutoService {

    List<KNeighbor> search(String name, KNode kNode, @NonNull List<KNode> all, int size);

    List<KNeighbor> search(String name, KNodeParam kNodeParam, int size);


    List<KNNParam> allKNNParams();

}
