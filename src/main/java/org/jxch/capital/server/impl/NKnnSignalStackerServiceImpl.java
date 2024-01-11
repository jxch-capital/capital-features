package org.jxch.capital.server.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.server.NKnnSignalStackerService;
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
