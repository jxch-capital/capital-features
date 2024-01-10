package org.jxch.capital.server.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KnowledgeParam;
import org.jxch.capital.http.vec.VecDbApi;
import org.jxch.capital.http.vec.dto.VecDbParam;
import org.jxch.capital.server.KnowledgeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VecDbKnowledgeServiceImpl implements KnowledgeService {
    private final VecDbApi vecDbApi;

    @Override
    public List<String> search(@NonNull KnowledgeParam param) {
        return vecDbApi.search(VecDbParam.builder().question(param.getQuestion()).num(param.getSimilarNum()).build()).getTexts();
    }

    @Override
    public String name() {
        return "向量知识库";
    }

}
