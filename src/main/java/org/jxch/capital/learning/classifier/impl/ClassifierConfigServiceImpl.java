package org.jxch.capital.learning.classifier.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jxch.capital.dao.ClassifierConfigRepository;
import org.jxch.capital.domain.convert.ClassifierConfigMapper;
import org.jxch.capital.domain.dto.ClassifierConfigDto;
import org.jxch.capital.learning.classifier.ClassifierConfigService;
import org.jxch.capital.learning.classifier.dto.ClassifierFitInfoDto;
import org.jxch.capital.utils.ReflectionsU;
import org.springframework.stereotype.Service;

import java.lang.reflect.Modifier;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClassifierConfigServiceImpl implements ClassifierConfigService {
    private final ClassifierConfigRepository classifierConfigRepository;
    private final ClassifierConfigMapper classifierConfigMapper;
    private List<ClassifierFitInfoDto> classifierFitInfos;
    private Set<Class<?>> supportParamTypes;

    @Override
    public List<ClassifierConfigDto> findAll() {
        return classifierConfigMapper.toClassifierConfigDto(classifierConfigRepository.findAll());
    }

    @Override
    public ClassifierConfigDto findById(Long id) {
        return classifierConfigMapper.toClassifierConfigDto(classifierConfigRepository
                .findById(id).orElseThrow(() -> new IllegalArgumentException("没有这个分类器ID: " + id)));
    }

    @Override
    public List<ClassifierConfigDto> findById(List<Long> ids) {
        return classifierConfigMapper.toClassifierConfigDto(classifierConfigRepository.findAllById(ids));
    }

    @Override
    public ClassifierConfigDto findByName(String name) {
        return classifierConfigMapper.toClassifierConfigDto(classifierConfigRepository.findByName(name));
    }

    @Override
    public Integer save(List<ClassifierConfigDto> dto) {
        return classifierConfigRepository.saveAllAndFlush(classifierConfigMapper.toClassifierConfig(dto)).size();
    }

    @Override
    public void del(List<Long> ids) {
        classifierConfigRepository.deleteAllById(ids);
    }

    @Override
    public List<ClassifierFitInfoDto> getAllClassificationSupportInfo() {
        return classifierFitInfos;
    }

    @Override
    public ClassifierFitInfoDto findClassifierFitInfoDto(String className, String fitTypes) {
        return classifierFitInfos.stream()
                .filter(info -> Objects.equals(info.getClassifierName(), className) && Objects.equals(info.getClassifierParamTypes(), fitTypes))
                .findAny().orElseThrow(() -> new IllegalArgumentException("不支持这种分类器: " + className + "(" + fitTypes + ")"));
    }

    @Override
    public List<ClassifierConfigDto> getAllClassifierSupportDto() {
        return classifierConfigMapper.toClassifierConfigDtoByClassifierFitInfoDto(getAllClassificationSupportInfo());
    }


    @SneakyThrows
    @PostConstruct
    public void init() {
        supportParamTypes = new HashSet<>(Arrays.asList(
                int.class, int[].class, int[][].class, Integer.class, Integer[].class, Integer[][].class,
                float.class, float[].class, float[][].class, Float.class, Float[].class, Float[][].class,
                double.class, double[].class, double[][].class, Double.class, Double[].class, Double[][].class,
                long.class, long[].class, long[][].class, Long.class, Long[].class, Long[][].class
        ));
        classifierFitInfos = ReflectionsU.scanAllPublicClass("classpath*:smile/classification/**/*.class").stream()
                .map(clazz -> ClassifierFitInfoDto.builder().classifierName(clazz.getSimpleName()).classifierClazz(clazz).build())
                .filter(classInfoDto -> !Modifier.isAbstract(classInfoDto.getClassifierClazz().getModifiers()))
                .filter(classInfoDto -> !classInfoDto.getClassifierClazz().isAnonymousClass())
                .flatMap(classInfoDto -> Arrays.stream(classInfoDto.getClassifierClazz().getMethods())
                        .filter(method -> Modifier.isStatic(method.getModifiers()))
                        .filter(method -> Objects.equals(method.getName(), "fit"))
                        .filter(method -> supportParamTypes.containsAll(Arrays.stream(method.getParameterTypes()).toList()))
                        .map(method -> classInfoDto.clone().setFitMethod(method).init()))
                .toList();
    }

}
