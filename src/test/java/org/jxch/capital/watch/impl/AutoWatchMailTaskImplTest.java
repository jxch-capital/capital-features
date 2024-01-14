package org.jxch.capital.watch.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class AutoWatchMailTaskImplTest {
    @Autowired
    private AutoWatchMailTaskImpl autoWatchMailTask;

    @Test
    void watchTask() {
        autoWatchMailTask.watchTask();
    }
}