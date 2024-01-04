package org.jxch.capital.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KNode {
    private String code;
    private List<KLine> kLines;

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

}
