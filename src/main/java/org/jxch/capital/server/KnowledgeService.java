package org.jxch.capital.server;

import org.jxch.capital.domain.dto.KnowledgeParam;

import java.util.List;

public interface KnowledgeService {

    List<String> search(KnowledgeParam param);

    default String name() {
        return getClass().getSimpleName();
    }

}
