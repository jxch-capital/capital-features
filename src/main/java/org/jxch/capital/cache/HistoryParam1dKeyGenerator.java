package org.jxch.capital.cache;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.domain.dto.HistoryParam;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Component("HistoryParam1dKeyGenerator")
public class HistoryParam1dKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, @NotNull Object... params) {
        return ((HistoryParam)params[0]).toString1d();
    }

}
