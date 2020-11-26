package com.example.app;

import com.example.app.service.Sender;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext("com.example.app");

        context.getBean(Sender.class).send("Hello, World!");


    }

}
