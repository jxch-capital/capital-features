package org.jxch.capital.learning.model.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@Slf4j
@SpringBootTest
class TensorflowModel3ManagementTest {
    @Autowired
    private TensorflowModel3Management tensorflowModel3Management;

    @Test
    void getModelFile() {
        File file = tensorflowModel3Management.getModelFile("");
        log.info(file.toPath().toString());
    }
}