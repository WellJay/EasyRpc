package com.welljay.easyrpc.test.jar;

import com.welljay.easyrpc.annotation.RpcService;

/**
 * @author wenjie
 * @description
 * @date 2019/8/12 22:27
 */
@RpcService
public interface HelloService {

    String hello();

}
