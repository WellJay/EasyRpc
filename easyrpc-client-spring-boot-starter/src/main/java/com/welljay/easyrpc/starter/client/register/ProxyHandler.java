package com.welljay.easyrpc.starter.client.register;

import com.welljay.easyrpc.client.RpcClient;
import com.welljay.easyrpc.server.RpcRequest;
import com.welljay.easyrpc.starter.client.properties.EasyRpcClientProperties;
import com.welljay.easyrpc.starter.client.threadpool.ThreadPool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;
import java.util.concurrent.Future;

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
        rpcRequest.setClassName(method.getDeclaringClass().getSimpleName());

        if(null != args) {
            Class<?>[] parameterTypesArr = getMethodParamTypesByParams(args);
            rpcRequest.setParameterTypes(parameterTypesArr);
        }

        Future<?> future = ThreadPool.REQUEST_THREAD_POOL.submit(() -> {
            RpcClient rpcClient = new RpcClient(EasyRpcClientProperties.IP, EasyRpcClientProperties.PORT);
            rpcClient.connect();
            Object result = rpcClient.send(rpcRequest);
            return result;
        });

        return future.get();
    }

}