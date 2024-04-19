package org.jxch.capital.stock.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@SpringBootTest
class K5MCNInfluxServiceImplTest {
    @Autowired
    private K5MCNInfluxServiceImpl k5MCNInfluxService;

    @Test
    void saveAllByCsvFiles() {
        File csvPath = new File("G:\\app\\backup\\data\\stock_data\\csv\\5-2");
        List<File> csvFiles = Arrays.stream(Objects.requireNonNull(csvPath.listFiles())).toList();
        Integer successNum = k5MCNInfluxService.saveAllByCsvFiles(csvFiles);
        log.info("successNum = {}", successNum);
    }

}