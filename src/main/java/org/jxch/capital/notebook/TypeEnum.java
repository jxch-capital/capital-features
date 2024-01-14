package org.jxch.capital.notebook;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeEnum {
    K_1("单K线"),
    K_N("连续的K线组合"),
    K_H_LINE("单K线高点水平线"),
    K_L_LINE("单K线低点水平线"),
    KK_H_LINE("两K线高点连线"),
    KK_L_LINE("两K线低点连线"),
    MARK("标记"),
    GLOBAL("全局类型");
    private final String remark;

}
