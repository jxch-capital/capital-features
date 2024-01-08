package org.jxch.capital.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KNode {
    private String code;
    private List<KLine> kLines;

    public KLine get(int index) {
        return kLines.get(index);
    }

    public KLine getBehind(int index) {
        return kLines.get(kLines.size() - index);
    }

    public KLine getFristKLine() {
        return kLines.get(0);
    }

    public KLine getLastKLine() {
        return kLines.get(kLines.size() - 1);
    }

    public KNode subtractLast(int num) {
        return KNode.builder()
                .code(code)
                .kLines(kLines.subList(0, kLines.size() - num))
                .build();
    }

    public KNode first(int num) {
        return KNode.builder()
                .code(code)
                .kLines(kLines.subList(0, num))
                .build();
    }

    public int size() {
        return kLines.size();
    }

    public List<KNode> slice() {
        List<KNode> kNodes = new ArrayList<>();
        for (int i = 0; i < kLines.size(); i++) {
            kNodes.add(KNode.builder().code(code)
                    .kLines(kLines.subList(0, kLines.size())).build());
        }
        return kNodes;
    }

    public List<KNode> sliceOut0() {
        List<KNode> slice = slice();
        return slice.subList(1, slice.size());
    }

    public List<KNode> slice(int size) {
        List<KNode> kNodes = new ArrayList<>();
        for (int i = 0; i < kLines.size() - size + 1; i++) {
            kNodes.add(KNode.builder().code(code)
                    .kLines(kLines.subList(i, i + size)).build());
        }
        return kNodes;
    }

    public List<KNode> sliceOut0(int size) {
        List<KNode> slice = slice(size);
        return slice.subList(1, slice.size());
    }

}
