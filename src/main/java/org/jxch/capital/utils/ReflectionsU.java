package org.jxch.capital.utils;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

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

    public static List<Class<?>> scanAllClassByClassPath(@NotNull String packageClassPath, @NotNull Function<Class<?>, Boolean> classFilter) {
        return scanAllClass("classpath*:" + packageClassPath.replaceAll("\\.", "/") + "/**/*.class").stream().filter(classFilter::apply).toList();
    }


    @NotNull
    @SneakyThrows
    public static <T> T newInstance(@NotNull Class<T> clazz) {
        return clazz.getDeclaredConstructor().newInstance();
    }

    @SneakyThrows
    public static <T> void setFieldValue(@NotNull T obj, String fieldName, Object value) {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }

    @NotNull
    @SneakyThrows
    @Contract(pure = true)
    public static Object getFieldValueNotNull(Object object, @NotNull Field field) {
        field.setAccessible(true);
        return Objects.requireNonNull(field.get(object));
    }

    @Nullable
    @SneakyThrows
    @Contract(pure = true)
    public static Object getFieldValueNullable(Object object, @NotNull Field field) {
        field.setAccessible(true);
        return field.get(object);
    }

    @SuppressWarnings("unchecked")
    public static <T> T createCGLIBProxyByInterface(Class<T> type, MethodInterceptor methodInterceptor) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Object.class);
        enhancer.setInterfaces(new Class[] { type });
        enhancer.setCallback(methodInterceptor);
        return (T) enhancer.create();
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public static <T> T createJDKProxyByInterface(@NotNull Class<T> type, InvocationHandler invocationHandler) {
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[] { type }, invocationHandler);
    }

    @NotNull
    public static List<Class<?>> getGenericsReturnType(@NotNull Method method) {
        List<Class<?>> types = new ArrayList<>();
        Type returnType = method.getGenericReturnType();

        if(returnType instanceof ParameterizedType) {
            for(Type typeArgument : ((ParameterizedType) returnType).getActualTypeArguments()) {
                types.add((Class<?>) typeArgument);
            }
        }

        return types;
    }

    public static Class<?> getSingleGenericReturnType(@NotNull Method method) {
        return getGenericsReturnType(method).get(0);
    }

}
