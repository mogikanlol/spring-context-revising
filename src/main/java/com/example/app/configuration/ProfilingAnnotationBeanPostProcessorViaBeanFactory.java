package com.example.app.configuration;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.lang.reflect.Proxy;

@RequiredArgsConstructor
public class ProfilingAnnotationBeanPostProcessorViaBeanFactory implements BeanPostProcessor {

    private final ConfigurableListableBeanFactory beanFactory;

    @Override
    @SneakyThrows
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);

        Class<?> originalClass = Class.forName(beanDefinition.getBeanClassName());

        if (originalClass.isAnnotationPresent(Profiling.class)) {
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
