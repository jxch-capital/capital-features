package org.jxch.capital.http.logic;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.http.logic.dto.BreathDto;
import org.jxch.capital.http.logic.dto.BreathParam;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BreathServiceImpl implements BreathService{
    private final BreathApi  breathApi;

    @Override
    public BreathDto getBreath(@NonNull BreathParam param) {
        return breathApi.getBreath().retained(param.getLength());
    }

}
