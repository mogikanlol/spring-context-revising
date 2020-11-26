package com.example.app.configuration;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class InjectRandomIntAnnotationBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        Class<?> aClass = bean.getClass();
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(InjectRandomInt.class)) {

                InjectRandomInt annotation = field.getAnnotation(InjectRandomInt.class);
                int max = annotation.max();
                int min = annotation.min();
                int randomInt = ThreadLocalRandom.current().nextInt(min, max);

                field.setAccessible(true);
                ReflectionUtils.setField(field, bean, randomInt);
            }
        }

        return bean;
    }
}
