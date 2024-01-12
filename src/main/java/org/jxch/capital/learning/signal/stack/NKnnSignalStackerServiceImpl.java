package org.jxch.capital.learning.signal.stack;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Slf4j
@Primary
@Service
@RequiredArgsConstructor
public class NKnnSignalStackerServiceImpl implements NKnnSignalStackerService {

    @Override
    public String name() {
        return "默认信号叠加器";
    }

}
