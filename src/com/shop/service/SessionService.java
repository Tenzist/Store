package com.shop.service;

public interface SessionService {

    void addParam(String key, Object object);

    Object getParam(String key);
}
