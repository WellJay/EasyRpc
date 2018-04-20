package com.welljay.easyrpc.client;

import com.welljay.easyrpc.server.RpcRequest;
import com.welljay.easyrpc.server.RpcResponse;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.UUID;

/**
 * @author wenjie
 * @date 2018/4/20 0020 13:49
 */
public class RpcClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setRequestId(UUID.randomUUID().toString());
        rpcRequest.setMethodName("hello");
        rpcRequest.setClassName("com.welljay.easyrpc.service.impl.EchoServiceImpl");
        //给服务器发消息
        ChannelFuture channelFuture = ctx.channel().writeAndFlush(rpcRequest);
        channelFuture.addListener((ChannelFutureListener) rfuture -> {
            if (!rfuture.isSuccess()) {
                System.out.println(rfuture);
            }
        });
        System.out.println("channelActive");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcResponse rpcResponse = (RpcResponse) msg;
        try {
            System.out.println(rpcResponse.getResult());
            ctx.close();
        }finally {

        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
