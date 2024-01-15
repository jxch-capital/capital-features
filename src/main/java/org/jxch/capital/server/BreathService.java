package org.jxch.capital.server;

import org.jxch.capital.http.logic.dto.BreathDto;
import org.jxch.capital.http.logic.dto.BreathParam;

public interface BreathService {

    BreathDto getBreath(BreathParam param);

}
