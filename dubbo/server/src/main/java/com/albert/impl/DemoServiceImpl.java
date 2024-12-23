package com.albert.impl;

import com.albert.api.DemoService;

public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return "Hello " + name + ", response from provider.";
    }
}
