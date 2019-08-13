package com.welljay.easyrpc.client.future;

import io.netty.util.concurrent.FastThreadLocal;

import java.util.HashMap;

/**
 * 保存客户端的请求future
 */
public class FutureHolder {

    private static final FastThreadLocal<HashMap<String, RpcFuture>> futureHolder = new FastThreadLocal<HashMap<String, RpcFuture>>() {
        @Override
        protected HashMap<String, RpcFuture> initialValue() {
            return new HashMap<>();
        }
    };

    public static void registerFuture(String requestId, RpcFuture future) {
        futureHolder.get().put(requestId, future);
    }

    public static RpcFuture getAndRemoveFuture(String requestId) {
        return futureHolder.get().remove(requestId);
    }


}