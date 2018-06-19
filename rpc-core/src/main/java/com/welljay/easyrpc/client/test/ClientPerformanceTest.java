package com.welljay.easyrpc.client.test;

import com.welljay.easyrpc.client.RpcClient;
import com.welljay.easyrpc.server.RpcRequest;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author wenjie
 * @date 2018/4/23 0023 11:24
 */
public class ClientPerformanceTest {

    private static AtomicLong callAmount = new AtomicLong(0L);

    public static void main(String[] args) throws InterruptedException, ClassNotFoundException, NoSuchMethodException {

        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setRequestId(UUID.randomUUID().toString());
        rpcRequest.setMethodName("hello");
        Object[] params = {"kok"};
        rpcRequest.setParameters(params);
        rpcRequest.setClassName("com.welljay.EchoServiceImpl");

        Class<?>[] parameterTypesArr = getMethodParamTypesByParams(params);

        rpcRequest.setParameterTypes(parameterTypesArr);

        int coreCount = Runtime.getRuntime().availableProcessors();

        CountDownLatch countDownLatch = new CountDownLatch(coreCount);

        long startTime = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(coreCount);

        for (int i = 0; i < coreCount; i++) {
            executorService.execute(() -> {
                while (callAmount.get() < 1000) {
                    RpcClient rpcClient = new RpcClient("127.0.0.1", 9000);
                    rpcClient.connect();
                    rpcClient.send(rpcRequest);
                    rpcClient.close();
                    callAmount.incrementAndGet();
                }
                countDownLatch.countDown();
            });
        }

        try {
            countDownLatch.await(300, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            //DO Nothing
        }

        long endTime = System.currentTimeMillis();
        Float tps = (float) callAmount.get() / (float) (endTime - startTime) * 1000F;
        StringBuilder sb = new StringBuilder();
        sb.append("tps:").append(tps);
        System.out.println(sb.toString());

        System.exit(1);
    }

    private static Class<?>[] getMethodParamTypesByParams(Object[] params) {
        Class<?>[] parameterTypesArr = new Class<?>[params.length];
        for (int i = 0; i < params.length; i++) {
            parameterTypesArr[i] = params[i].getClass();
        }
        return parameterTypesArr;
    }
}
