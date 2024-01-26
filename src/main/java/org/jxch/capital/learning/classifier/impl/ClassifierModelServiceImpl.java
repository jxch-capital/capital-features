package org.jxch.capital.learning.classifier.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.domain.dto.*;
import org.jxch.capital.learning.classifier.ClassifierConfigService;
import org.jxch.capital.learning.classifier.ClassifierLearnings;
import org.jxch.capital.learning.classifier.ClassifierModelConfigService;
import org.jxch.capital.learning.classifier.ClassifierModelService;
import org.jxch.capital.learning.classifier.config.ClassifierLearningConfig;
import org.jxch.capital.learning.classifier.dto.ClassifierFitInfoDto;
import org.jxch.capital.learning.classifier.dto.ClassifierFitParam;
import org.jxch.capital.stock.IntervalEnum;
import org.jxch.capital.server.KNodeService;
import org.jxch.capital.utils.KLineSignals;
import org.jxch.capital.utils.KNodes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smile.classification.Classifier;

import java.io.*;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassifierModelServiceImpl implements ClassifierModelService {
    private final ClassifierLearningConfig classifierLearningConfig;
    private final ClassifierModelConfigService classifierModelConfigService;
    private final ClassifierConfigService classifierConfigService;
    private final KNodeService kNodeService;

    @Override
    public List<ClassifierModelConfigDto> findModelConfigsHasLocal() {
        return findAllModelConfig().stream().filter(ClassifierModelConfigDto::isHasLocalModel).toList();
    }

    @Override
    public List<ClassifierModelConfigDto> findAllModelConfig() {
        return classifierModelConfigService.findAll();
    }

    @Override
    public ClassifierModelConfigDto findModelConfigById(Long id) {
        return classifierModelConfigService.findById(id);
    }

    @Override
    public List<ClassifierModelConfigDto> findModelConfigById(List<Long> ids) {
        return classifierModelConfigService.findById(ids);
    }

    @Override
    public ClassifierModelConfigDto findModelConfigByName(String name) {
        return classifierModelConfigService.findByName(name);
    }

    @Override
    public List<ClassifierModelConfigDto> findModelConfigByName(List<String> names) {
        return classifierModelConfigService.findByName(names);
    }

    private void renameModel(String oldModelName, String newModelName) {
        if (hasLocalModel(oldModelName)) {
            File oldFile = new File(ClassifierLearnings.getModePath(oldModelName));
            File newFile = new File(ClassifierLearnings.getModePath(newModelName));
            if (oldFile.renameTo(newFile)) {
                log.debug("模型重命名：{} -> {}", newModelName, newModelName);
            } else {
                log.error("模型重命名失败：{} -> {}", oldModelName, newModelName);
            }
        }
    }

    @Override
    @Transactional
    public Integer saveModelConfig(@NonNull List<ClassifierModelConfigDto> dto) {
        dto.forEach(d -> {
            if (Objects.nonNull(d.getId())) {
                String oldName = findModelConfigById(d.getId()).getName();
                if (!oldName.equals(d.getName())) {
                    renameModel(oldName, d.getName());
                }
            }
        });
        return classifierModelConfigService.save(dto);
    }

    @Override
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public Classifier<double[]> findModel(String modeName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ClassifierLearnings.getModePath(modeName)))) {
            return (Classifier<double[]>) ois.readObject();
        }
    }

    @Override
    @SneakyThrows
    public void saveModel(Classifier<double[]> classifier, String modeName) {
        if (ClassifierLearnings.hasLocalModel(modeName)) {
            delModel(modeName);
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ClassifierLearnings.getModePath(modeName)))) {
            oos.writeObject(classifier);
            log.debug("模型保存成功：{}", ClassifierLearnings.getModePath(modeName));
        }
    }

    @Override
    @SneakyThrows
    public void delModel(String modeName) {
        String modelPath = ClassifierLearnings.getModePath(modeName);
        File modelFile = new File(modelPath);
        if (modelFile.exists()) {
            if (modelFile.delete()) {
                log.debug("模型删除成功：{}", modelPath);
            } else {
                log.error("模型删除失败：{}", modelPath);
            }
        }
    }

    @Override
    @Transactional
    public void delModelAnConfig(List<Long> ids) {
        findModelConfigById(ids).forEach(config -> delModel(config.getName()));
        classifierModelConfigService.del(ids);
    }

    @Override
    @SneakyThrows
    public void fit(Long id) {
        ClassifierModelConfigDto classifierModelConfig = classifierModelConfigService.findById(id);
        ClassifierConfigDto classifierConfig = classifierConfigService.findById(classifierModelConfig.getClassifierId());
        ClassifierFitInfoDto classifierFitInfoDto = classifierConfigService.findClassifierFitInfoDto(
                classifierConfig.getClassifierName(), classifierConfig.getClassifierParamTypes());


        Class<?>[] types = classifierFitInfoDto.getFitMethod().getParameterTypes();
        List<String> paramsList = classifierConfig.getClassifierParamsList();
        Object[] params = new Object[types.length];

        ClassifierFitParam fitParam = new ClassifierFitParam();
        for (int i = 0; i < types.length; i++) {
            if (this.isXT(paramsList.get(i))) {
                KNodeParam kNodeParam = KNodeParam.builder()
                        .size(classifierModelConfig.getSize() + classifierModelConfig.getFutureNum())
                        .stockPoolId(classifierModelConfig.getStockPoolId())
                        .indicesComId(classifierModelConfig.getIndicesComId())
                        .normalized(true)
                        .build();

                List<KNode> kNodesT = kNodeService.comparison(kNodeParam);
                fitParam.setKNodesT(kNodesT).setFutureNum(classifierModelConfig.getFutureNum());

                if (kNodeParam.hasIndicesComId()) {
                    fitParam.setTDataByIndicesH();
                } else {
                    fitParam.setTDataByKLineH();
                }

                params[i] = fitParam.getXT();
            } else if (this.isYT(paramsList.get(i))) {
                params[i] = fitParam.getYT();
            } else {
                Class<?> typeClazz = types[i];
                String param = paramsList.get(i);
                if (typeClazz.getName().equals(double.class.getName())) {
                    params[i] = Double.parseDouble(param);
                } else if (typeClazz.getName().equals(int.class.getName())) {
                    params[i] = Integer.parseInt(param);
                } else {
                    params[i] = types[i].cast(paramsList.get(i));
                }
            }
        }

        @SuppressWarnings("unchecked")
        Classifier<double[]> classifier = (Classifier<double[]>) classifierFitInfoDto.getFitMethod().invoke(null, params);
        saveModel(classifier, classifierModelConfig.getName());
    }

    @Override
    public List<KLineSignal> predict(@NonNull ClassifierPredictParam param) {
        ClassifierModelConfigDto config = classifierModelConfigService.findById(param.getClassifierModelId());

        // todo 支持其他时间间隔
        KNodeParam kNodeParam = KNodeParam.builder()
                .normalized(true)
                .code(param.getCode())
                .size(config.getSize() + config.getFutureNum())
                .intervalEnum(IntervalEnum.DAY_1)
                .indicesComId(config.getIndicesComId())
                .build();

        List<KNode> sourceKNodes = kNodeService.kNodes(kNodeParam, param.getStart(), Calendar.getInstance().getTime());
        List<KLineSignal> kLineSignals = KLineSignals.setTrueSignalHasLastNull(sourceKNodes, config.getFutureNum(), config.getSize());

        List<KNode> kNodes = KNodes.sliceAndSubtractLastFuture(sourceKNodes, config.getFutureNum(), config.getSize());
        Classifier<double[]> model = findModel(config.getName());
        int[] predictSignal = config.hasIndicesCombination() ?
                model.predict(KNodes.normalizedIndicesArrH(kNodes)) : model.predict(KNodes.normalizedKArrH(kNodes));

        for (int i = 0; i < predictSignal.length; i++) {
            kLineSignals.get(i).setSignal((double) predictSignal[i]);
        }

        return kLineSignals;
    }

    @Override
    public List<String> findAllLocalModelNames() {
        return Arrays.stream(Objects.requireNonNull(new File(classifierLearningConfig.getModelPath()).listFiles()))
                .map(file -> file.getName().substring(0, file.getName().length() - classifierLearningConfig.getModelSuffix().length()))
                .toList();
    }

    @Override
    public boolean hasLocalModel(String modelName) {
        return new File(ClassifierLearnings.getModePath(modelName)).exists();
    }

    private boolean isXT(String paramValue) {
        return Objects.equals(paramValue, ClassifierModelService.XT_PARAM_NAME);
    }

    private boolean isYT(String paramValue) {
        return Objects.equals(paramValue, ClassifierModelService.YT_PARAM_NAME);
    }

}
