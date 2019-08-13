package com.welljay.easyrpc.client;

import com.welljay.easyrpc.client.future.FutureHolder;
import com.welljay.easyrpc.client.future.RpcFuture;
import com.welljay.easyrpc.server.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author wenjie
 * @date 2018/4/20 0020 13:49
 */
public class RpcClientHandler extends SimpleChannelInboundHandler<RpcResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponse rpcResponse) throws Exception {
        RpcFuture rpcFuture = FutureHolder.getAndRemoveFuture(rpcResponse.getRequestId());
        if (rpcFuture != null) {
            rpcFuture.setSuccess(rpcResponse);
        }
    }
}
