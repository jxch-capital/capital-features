package org.jxch.capital.learning.classifier;

import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.learning.classifier.config.ClassifierLearningConfig;
import org.jxch.capital.learning.classifier.dto.ClassifierLearningParam;
import org.jxch.capital.learning.classifier.model.ClassifierLearningService;
import org.jxch.capital.utils.AppContextHolder;
import org.jxch.capital.utils.KNodes;
import org.springframework.stereotype.Component;
import smile.classification.Classifier;
import smile.classification.SVM;
import smile.math.kernel.GaussianKernel;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClassifierLearnings {
    private final ClassifierLearningConfig classifierLearningConfig;
    private static ClassifierLearningConfig CONFIG;

    @PostConstruct
    public void init() {
        CONFIG = classifierLearningConfig;
    }

    @NotNull
    public static String getModePath(String modeName) {
        return CONFIG.getModelPath() + modeName + CONFIG.getModelSuffix();
    }

    public static boolean hasLocalModel(String modeName) {
        return new File(ClassifierLearnings.getModePath(modeName)).exists();
    }

    public static ClassifierLearningParam SVMGaussianKernel() {
        return ClassifierLearningParam.defaultParam()
                .setKernel(new GaussianKernel(2.0))
                .setClassifierFitFunc(param -> SVM.fit(param.getXT(), param.getYT(), param.getC(), param.getTol(), param.getEpochs()));
    }

    public static ClassifierLearningParam setDataByKLineH(@NonNull ClassifierLearningParam emptyParam) {
        return emptyParam
                .setYT(KNodes.futures(emptyParam.getKNodesT(), emptyParam.getFutureNum()))
                .setXT(KNodes.normalizedKArrH(KNodes.subtractLast(emptyParam.getKNodesT(), emptyParam.getFutureNum())))
                .setXP(KNodes.normalizedKArrH(KNodes.sliceLastFuture(emptyParam.getKNodesP(), emptyParam.getFutureNum(), emptyParam.getSize())));
    }

    public static ClassifierLearningParam setDataByKLineV(@NonNull ClassifierLearningParam emptyParam) {
        return emptyParam
                .setYT(KNodes.futures(emptyParam.getKNodesT(), emptyParam.getFutureNum()))
                .setXT(KNodes.normalizedKArrV(KNodes.subtractLast(emptyParam.getKNodesT(), emptyParam.getFutureNum())))
                .setXP(KNodes.normalizedKArrV(KNodes.sliceLastFuture(emptyParam.getKNodesP(), emptyParam.getFutureNum(), emptyParam.getSize())));
    }

    public static List<ClassifierLearningService> allClassifierLearningService() {
        return AppContextHolder.getContext().getBeansOfType(ClassifierLearningService.class).values().stream().toList();
    }

    public static List<String> allClassifierLearningServiceNames() {
        return allClassifierLearningService().stream().map(ClassifierLearningService::name).toList();
    }

    public static ClassifierLearningService getClassifierLearningService(String name) {
        return allClassifierLearningService().stream().filter(service -> Objects.equals(name, service.name()))
                .findAny().orElseThrow(() -> new IllegalArgumentException("没有这个分类器：" + name));
    }

    @NotNull
    public static List<String> allLocalModel(String modelPath) {
        List<String> models = new ArrayList<>();

        File[] files = new File(modelPath).listFiles();
        if (files != null) {
            models.addAll(Arrays.stream(files).map(File::getName).toList());
        }

        return models;
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public static Classifier<double[]> load(String modelNamePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(modelNamePath))) {
            return (Classifier<double[]>) ois.readObject();
        }
    }

    @SneakyThrows
    public static void save(@NonNull Classifier<double[]> classifier, String modelNamePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(modelNamePath))) {
            oos.writeObject(classifier);
        }
    }

}
