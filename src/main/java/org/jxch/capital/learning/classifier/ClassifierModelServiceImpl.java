package org.jxch.capital.learning.classifier;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.learning.classifier.config.ClassifierLearningConfig;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassifierModelServiceImpl implements ClassifierModelService {
    private final ClassifierLearningConfig classifierLearningConfig;

    // todo 模型定制服务（参数存入数据库），更新模型保存到本地

}
