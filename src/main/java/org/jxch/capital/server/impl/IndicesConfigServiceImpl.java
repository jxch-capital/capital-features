package org.jxch.capital.server.impl;

import com.google.common.reflect.ClassPath;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.dao.IndicesConfigRepository;
import org.jxch.capital.domain.convert.IndicesConfigMapper;
import org.jxch.capital.domain.dto.IndicesConfigDto;
import org.jxch.capital.server.IndicesConfigService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.num.Num;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class IndicesConfigServiceImpl implements IndicesConfigService {
    private final static String SUFFIX = "Indicator";
    private final static String BAR = "BarSeries";
    @Getter
    private final IndicesConfigRepository indicesConfigRepository;
    private final IndicesConfigMapper indicesConfigMapper;
    private Map<String, Class<?>> indicatorsClazzMap;
    private Map<String, Class<?>> supportTypeClazzMap;

    @PostConstruct
    public void init() throws IOException {
        ClassPath classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());
        indicatorsClazzMap = classPath.getTopLevelClassesRecursive("org.ta4j.core.indicators").stream()
                .collect(Collectors.toMap(ClassPath.ClassInfo::getSimpleName, ClassPath.ClassInfo::load));
        supportTypeClazzMap = Stream.of(Integer.class, Double.class, Indicator.class, BarSeries.class, Number.class, Num.class)
                .collect(Collectors.toMap(Class::getSimpleName, Function.identity()));
        supportTypeClazzMap.put("int", int.class);
    }

    @Override
    public Integer save(List<IndicesConfigDto> dtoList) {
        return indicesConfigRepository.saveAllAndFlush(indicesConfigMapper.toIndicesConfig(dtoList)).size();
    }

    @Override
    public void del(List<Long> ids) {
        indicesConfigRepository.deleteAllById(ids);
    }

    @Override
    public List<IndicesConfigDto> findAll() {
        return indicesConfigMapper.toIndicesConfigDto(indicesConfigRepository.findAll());
    }

    @Override
    public List<IndicesConfigDto> findById(List<Long> ids) {
        return indicesConfigMapper.toIndicesConfigDto(indicesConfigRepository.findAllById(ids));
    }

    @Override
    public IndicesConfigDto findById(Long id) {
        return indicesConfigMapper.toIndicesConfigDto(indicesConfigRepository.findById(id).orElseThrow());
    }

    @Override
    public IndicesConfigDto findByName(String name) {
        return indicesConfigMapper.toIndicesConfigDto(indicesConfigRepository.findByName(name));
    }

    @Override
    public List<IndicesConfigDto> findByName(List<String> name) {
        return indicesConfigMapper.toIndicesConfigDto(indicesConfigRepository.findByName(name));
    }

    @Override
    public Indicator<Num> getIndicatorById(Long id, BarSeries barSeries) {
        return getIndicator(findById(id), barSeries);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    private Indicator<Num> getIndicator(@NonNull IndicesConfigDto dto, BarSeries barSeries) {
        try {
            return (Indicator<Num>) indicatorsClazzMap.get(dto.getIndexName() + SUFFIX)
                    .getConstructor(getParamTypes(dto).toArray(Class[]::new))
                    .newInstance(getParams(dto, barSeries));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    @NonNull
    private Object[] getParams(@NonNull IndicesConfigDto dto, BarSeries barSeries) {
        List<String> sourceParams = dto.getIndexParamsList();
        List<String> types = dto.getIndexParamTypeList();
        List<Object> params = new ArrayList<>();

        for (int i = 0; i < types.size(); i++) {
            if (Objects.equals(types.get(i), SUFFIX)) {
                params.add(getIndicatorById(Long.valueOf(sourceParams.get(i)), barSeries));
            } else if (Objects.equals(types.get(i), BAR)) {
                params.add(barSeries);
            } else {
                Class<?> typeClazz = supportTypeClazzMap.get(types.get(i));
                if (Objects.equals(typeClazz, int.class)) {
                    params.add(Integer.valueOf(sourceParams.get(i)));
                } else if (Objects.equals(typeClazz, double.class)) {
                    params.add(Double.valueOf(sourceParams.get(i)));
                } else {
                    params.add(supportTypeClazzMap.get(types.get(i)).cast(sourceParams.get(i)));
                }
            }
        }
        return params.toArray();
    }

    @Override
    @Cacheable(cacheNames = "allSupportIndicators")
    public List<String> allSupportIndicators() {
        return this.indicatorsClazzMap.values().stream()
                .map(clazz -> clazz.getDeclaredConstructors()[0].toString())
                .filter(s -> s.contains("public"))
                .map(s -> s.replaceAll("\\(.*", "")
                        .replaceAll(".*\\.", "")
                        .replaceAll(SUFFIX, "")
                        + "(" + s.replaceAll(".*\\(", "")
                        .replaceAll("org.ta4j.*Indicator", "Indicator")
                        .replaceAll("org.ta4j.*BarSeries", "BarSeries")
                        .replaceAll("org\\..*\\.Num", "Num")
                        .replaceAll("java\\..*\\.Number", "Number")
                        .replaceAll("org\\..*\\.indicators\\.", "")
                        .replaceAll("java\\..*\\.function\\.", "")
                        .replaceAll("\\$.*Type", "Type")
                )
                .filter(s -> Arrays.stream(s.replaceAll(".*\\(", "").replaceAll("\\)", "")
                        .split(",")).allMatch(param -> supportTypeClazzMap.containsKey(param)))
                .sorted()
                .toList();
    }

    @Override
    public List<Class<?>> allSupportIndicatorParamTypes() {
        return new ArrayList<>(supportTypeClazzMap.values());
    }

    @Override
    public List<Class<?>> getParamTypes(@NonNull IndicesConfigDto dto) {
        List<Class<?>> supportTypes = new ArrayList<>();
        for (String type : dto.getIndexParamTypeList()) {
            supportTypes.add(supportTypeClazzMap.get(type));
        }
        return supportTypes;
    }

    @Override
    public boolean hasIndicatorParam(@NonNull IndicesConfigDto dto) {
        return dto.getIndexParamTypes().contains(SUFFIX);
    }

}
