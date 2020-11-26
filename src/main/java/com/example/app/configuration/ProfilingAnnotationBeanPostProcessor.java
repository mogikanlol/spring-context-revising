package com.example.app.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProfilingAnnotationBeanPostProcessor implements BeanPostProcessor {

    private Map<String, Class<?>> map = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        Class<?> aClass = bean.getClass();

        if (aClass.isAnnotationPresent(Profiling.class)) {
            map.put(beanName, aClass);
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        Class<?> originalClass = map.get(beanName);
        if (originalClass != null) {
            return Proxy.newProxyInstance(originalClass.getClassLoader(), originalClass.getInterfaces(), (proxy, method, args) -> {

                System.out.println("Profiling has started");
                long before = System.nanoTime();

                Object returnValue = method.invoke(bean, args);

                long after = System.nanoTime();
                System.out.println(after - before);
                System.out.println("Profiling has ended");

                return returnValue;
            });
        }

        return bean;
    }
}
