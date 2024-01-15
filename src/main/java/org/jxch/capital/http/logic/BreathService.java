package org.jxch.capital.http.logic;

import org.jxch.capital.http.logic.dto.BreathDto;
import org.jxch.capital.http.logic.dto.BreathParam;

public interface BreathService {

    BreathDto getBreath(BreathParam param);

}
