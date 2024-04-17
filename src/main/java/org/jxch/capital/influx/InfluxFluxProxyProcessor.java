package org.jxch.capital.influx;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.query.FluxTable;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.plexus.util.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jxch.capital.FeaturesApp;
import org.jxch.capital.config.InfluxDBConfig;
import org.jxch.capital.utils.ReflectionsU;
import org.jxch.capital.utils.StringU;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

@Slf4j
@Component
public class InfluxFluxProxyProcessor implements MethodInterceptor, InvocationHandler, BeanFactoryPostProcessor {

    @Override
    @SneakyThrows
    public Object intercept(Object obj, @NotNull Method method, Object[] args, MethodProxy proxy) {
        return invoke(obj, method, args);
    }

    @Override
    public Object invoke(Object proxy, @NotNull Method method, Object[] args) throws Throwable {
        if (method.isDefault()) {
            final Class<?> declaringClass = method.getDeclaringClass();
            MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(declaringClass, MethodHandles.lookup());
            MethodHandle handle = lookup.findSpecial(declaringClass, method.getName(),
                    MethodType.methodType(method.getReturnType(), method.getParameterTypes()), declaringClass);
            return handle.bindTo(proxy).invokeWithArguments(args);
        }

        InfluxDBClient influxDBClient = SpringUtil.getBean(InfluxDBClient.class);
        if (AnnotationUtil.hasAnnotation(method, InfluxFluxQuery.class)) {
            String flux = StringU.parameterExpression(AnnotationUtil.getAnnotation(method, InfluxFluxQuery.class).flux(), args);
            List<FluxTable> fluxTables = influxDBClient.getQueryApi().query(flux, SpringUtil.getBean(InfluxDBConfig.class).getOrg());
            return InfluxPoints.toPointDto(fluxTables, ReflectionsU.getSingleGenericReturnType(method));
        }

        throw new UnsupportedOperationException("Unsupported method: " + method);
    }

    @Override
    public void postProcessBeanFactory(@NotNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
        for (Class<?> targetApi : ReflectionsU.scanAllClassByClassPath(FeaturesApp.class.getPackageName(), clazz -> InfluxApi.class.isAssignableFrom(clazz) && clazz.isInterface())) {
            beanFactory.registerSingleton(StringUtils.lowercaseFirstLetter(targetApi.getName()), ReflectionsU.createJDKProxyByInterface(targetApi, this));
        }
    }

}
