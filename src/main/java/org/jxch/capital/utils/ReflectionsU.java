package org.jxch.capital.utils;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionsU {

    public static boolean hasPublicConstructor(@NonNull Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredConstructors()).anyMatch(constructor -> Modifier.isPublic(constructor.getModifiers()));
    }

    @NotNull
    @SneakyThrows
    public static List<Class<?>> scanAllClass(String packagePath) {
        List<Class<?>> classifierClass = new ArrayList<>();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources(packagePath);
        for (Resource resource : resources) {
            MetadataReader metadataReader = new CachingMetadataReaderFactory().getMetadataReader(resource);
            Class<?> clazz = Class.forName(metadataReader.getClassMetadata().getClassName());
            classifierClass.add(clazz);
        }
        return classifierClass;
    }

    public static List<Class<?>> scanAllPublicClass(String packagePath) {
        return scanAllClass(packagePath).stream()
                .filter(clazz -> !clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers()) && hasPublicConstructor(clazz))
                .toList();
    }

}
