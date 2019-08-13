package com.welljay.easyrpc.test.server.service.impl;

import com.welljay.easyrpc.annotation.RpcService;
import com.welljay.easyrpc.test.server.service.HelloService;

/**
 * @author wenjie
 * @description
 * @date 2019/8/12 22:28
 */
@RpcService
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello() {
        return "hello from server...";
    }
}
