package org.jxch.capital.learning.signal.stack;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.KLineSignalStackDto;
import org.jxch.capital.domain.dto.KnnSignalConfigDto;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class NKnnSignalSameActionStackerServiceImpl implements NKnnSignalStackerService {

    @Override
    public void setActionSignal(@NonNull KLineSignalStackDto sourceDto, @NonNull KLineSignalStackDto newDtoOnlySignal, KnnSignalConfigDto newConfig) {
        sourceDto.setActionSignal(Objects.equals(sourceDto.getActionSignal(), newDtoOnlySignal.getActionSignal()) ?
                sourceDto.getActionSignal() : 0);
    }

    @Override
    public String name() {
        return "同向Action";
    }

}
