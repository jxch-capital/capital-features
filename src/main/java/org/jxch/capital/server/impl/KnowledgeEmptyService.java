package org.jxch.capital.server.impl;

import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KnowledgeParam;
import org.jxch.capital.server.KnowledgeService;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class KnowledgeEmptyService implements KnowledgeService {

    @Override
    public List<String> search(KnowledgeParam param) {
        return new LinkedList<>();
    }

    @Override
    public String name() {
        return "";
    }

}
