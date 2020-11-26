package com.example.app.service;

import com.example.app.configuration.InjectRandomInt;
import com.example.app.configuration.PostProxy;
import com.example.app.configuration.Profiling;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Profiling
public class HttpSender implements Sender {

    @InjectRandomInt(min = 2, max = 4)
    private int repeat;

    public HttpSender() {
        System.out.println("Phase 1");
        System.out.println("Repeats: " + repeat);
    }

    @PostConstruct
    private void initMethod() {
        System.out.println("Phase 2");
        System.out.println("Repeats: " + repeat);
    }

    @PostProxy
    // should be declared in the interface because java dynamic proxy works via interfaces
    public void initAfterProxyInitialized() {
        System.out.println("Phase 3");
    }

    @Override
    public void send(String message) {
        for (int i = 0; i < repeat; i++) {
            System.out.printf("Sending \"%s\" over http...%n", message);
        }
    }
}
