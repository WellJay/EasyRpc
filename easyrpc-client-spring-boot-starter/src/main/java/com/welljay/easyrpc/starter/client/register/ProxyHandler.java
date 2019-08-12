package com.welljay.easyrpc.starter.client.register;

import com.welljay.easyrpc.client.RpcClient;
import com.welljay.easyrpc.server.RpcRequest;
import com.welljay.easyrpc.starter.client.properties.EasyRpcProperties;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

public class ProxyHandler implements InvocationHandler {

    private Class<?> interfaceClass;


    public Object bind(Class<?> cls) {
        this.interfaceClass = cls;
        return Proxy.newProxyInstance(cls.getClassLoader(), new Class[] {interfaceClass}, this);
    }


    private static Class<?>[] getMethodParamTypesByParams(Object[] params) {
        Class<?>[] parameterTypesArr = new Class<?>[params.length];
        for (int i = 0; i < params.length; i++) {
            parameterTypesArr[i] = params[i].getClass();
        }
        return parameterTypesArr;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setRequestId(UUID.randomUUID().toString());
        rpcRequest.setMethodName(method.getName());

        rpcRequest.setParameters(args);
        rpcRequest.setClassName(method.getDeclaringClass().getName());

        if(null != args) {
            Class<?>[] parameterTypesArr = getMethodParamTypesByParams(args);
            rpcRequest.setParameterTypes(parameterTypesArr);
        }


        //todo 可优化
        RpcClient rpcClient = new RpcClient(EasyRpcProperties.IP, EasyRpcProperties.PORT);
        rpcClient.connect();
        rpcClient.send(rpcRequest);
        rpcClient.close();

        return null;
    }

}