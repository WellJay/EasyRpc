package com.welljay.easyrpc.client.test;

import com.welljay.easyrpc.service.EchoService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

/**
 * Created by wenjie on 2018/6/15 0015.
 */
public class ClientTest {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ProxyHandler proxyHandler = new ProxyHandler();

        EchoService echoService = (EchoService) Proxy.newProxyInstance(EchoService.class.getClassLoader(), new Class<?>[] {EchoService.class}, proxyHandler);

        echoService.hello();
    }


}
