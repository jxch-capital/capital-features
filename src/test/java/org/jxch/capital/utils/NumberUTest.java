package org.jxch.capital.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class NumberUTest {

    @Test
    void fillLong() {
        log.info("{}", NumberU.fillLong("123", 4));
    }

    @Test
    void incFillLong() {
        log.info("{}", NumberU.incFillLong("123", 4));
    }
}