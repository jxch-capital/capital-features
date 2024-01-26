package org.jxch.capital.learning.model.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@Slf4j
@SpringBootTest
class TensorflowMinioModel3ManagementTest {
    @Autowired
    private TensorflowMinioModel3Management tensorflowMinioModel3Management;

    @Test
    void getModelFile() {
        File file = tensorflowMinioModel3Management.getModelFile("");
        log.info(file.toPath().toString());
    }
}