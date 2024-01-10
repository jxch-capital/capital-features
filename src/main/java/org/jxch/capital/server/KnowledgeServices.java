package org.jxch.capital.server;

import org.jxch.capital.utils.AppContextHolder;

import java.util.List;
import java.util.Objects;

public class KnowledgeServices {

    public static List<KnowledgeService> allKnowledgeServices() {
        return AppContextHolder.getContext().getBeansOfType(KnowledgeService.class).values().stream().toList();
    }

    public static List<String> allKnowledgeServiceNames() {
        return allKnowledgeServices().stream().map(KnowledgeService::name).sorted().toList();
    }

    public static KnowledgeService getKnowledgeService(String name) {
        return allKnowledgeServices().stream().filter(knowledgeService -> Objects.equals(knowledgeService.name(), name))
                .findAny().orElseThrow(() -> new IllegalArgumentException("没有这个知识服务：" + name));
    }



}
