package com.example.app.service;

public interface Sender {

    void initAfterProxyInitialized();

    void send(String message);

}
