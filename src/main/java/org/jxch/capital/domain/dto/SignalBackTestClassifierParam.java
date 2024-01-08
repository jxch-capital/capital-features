package org.jxch.capital.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SignalBackTestClassifierParam extends SignalBackTestParam {
    private String classifierName;
    private KNodeParam kNodeParam = new KNodeParam();
}
