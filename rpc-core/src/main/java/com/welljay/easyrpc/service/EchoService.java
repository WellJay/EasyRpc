package com.welljay.easyrpc.service;

import com.welljay.easyrpc.annotation.RpcService;

/**
 * @author wenjie
 * @date 2018/4/20 0020 11:53
 */
@RpcService
public interface EchoService {

    String hello();

    String hello(String name);

    String test();
}
