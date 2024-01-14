package org.jxch.capital.notebook;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KeyEnum {
    SIGNAL("买卖信号"),
    K_PATTERN("K线形态"),
    IMG_URL("图片连接")
    ;
    private final String remark;

}
