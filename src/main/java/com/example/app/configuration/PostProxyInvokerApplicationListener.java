package com.example.app.configuration;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
@RequiredArgsConstructor
public class PostProxyInvokerApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private final ConfigurableListableBeanFactory beanFactory;

    @Override
    @SneakyThrows
    public void onApplicationEvent(ContextRefreshedEvent event) {

        ApplicationContext context = event.getApplicationContext();

        for (String beanName : context.getBeanDefinitionNames()) {

            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            String beanClassName = beanDefinition.getBeanClassName();
            Class<?> originalClass = Class.forName(beanClassName);

            Method[] methods = originalClass.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(PostProxy.class)) {
                    Object bean = context.getBean(beanName);
                    Method beanMethod = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
                    beanMethod.invoke(bean);
                }
            }
        }

    }
}
