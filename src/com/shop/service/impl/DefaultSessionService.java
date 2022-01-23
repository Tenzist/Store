package com.shop.service.impl;

import com.shop.service.SessionService;

import java.util.HashMap;
import java.util.Map;

public class DefaultSessionService implements SessionService {

    private Map<String,Object> params = new HashMap<>();

    @Override
    public void addParam(String key, Object object) {
        params.put(key, object);
    }

    @Override
    public Object getParam(String key) {
        return params.get(key);
    }
}
