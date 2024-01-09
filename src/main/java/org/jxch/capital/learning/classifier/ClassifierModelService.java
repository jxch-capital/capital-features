package org.jxch.capital.learning.classifier;

import org.jxch.capital.domain.dto.ClassifierModelConfigDto;
import org.jxch.capital.domain.dto.ClassifierPredictParam;
import org.jxch.capital.domain.dto.KLineSignal;
import smile.classification.Classifier;

import java.util.List;

public interface ClassifierModelService {
    String XT_PARAM_NAME = "x";
    String YT_PARAM_NAME = "y";

    List<ClassifierModelConfigDto> findModelConfigsHasLocal();
    List<ClassifierModelConfigDto> findAllModelConfig();

    ClassifierModelConfigDto findModelConfigById(Long id);

    List<ClassifierModelConfigDto> findModelConfigById(List<Long> ids);

    ClassifierModelConfigDto findModelConfigByName(String name);

    List<ClassifierModelConfigDto> findModelConfigByName(List<String> names);

    Integer saveModelConfig(List<ClassifierModelConfigDto> dto);

    Classifier<double[]> findModel(String modeName);

    void saveModel(Classifier<double[]> classifier, String modeName);

    void delModel(String modeName);

    void delModelAnConfig(List<Long> ids);

    void fit(Long id);

    List<KLineSignal> predict(ClassifierPredictParam param);

    List<String> findAllLocalModelNames();

    boolean hasLocalModel(String modelName);

}
