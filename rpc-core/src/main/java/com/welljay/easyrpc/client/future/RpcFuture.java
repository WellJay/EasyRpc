package com.welljay.easyrpc.client.future;

import com.welljay.easyrpc.server.RpcResponse;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;

/**
 * welljay
 */
public class RpcFuture extends DefaultPromise<RpcResponse> {
    public RpcFuture(EventExecutor executor) {
        super(executor);
    }
}