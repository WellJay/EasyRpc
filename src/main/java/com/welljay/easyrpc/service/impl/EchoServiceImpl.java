package com.welljay.easyrpc.service.impl;

import com.welljay.easyrpc.annotation.RpcService;
import com.welljay.easyrpc.service.EchoService;

/**
 * @author wenjie
 * @date 2018/4/20 0020 12:02
 */
@RpcService
public class EchoServiceImpl implements EchoService {

    @Override
    public String hello() {
        return "hello";
    }

    @Override
    public String hello(String name) {
        return "hello " + name;
    }

    @Override
    public String test() {
        return "test";
    }
}
