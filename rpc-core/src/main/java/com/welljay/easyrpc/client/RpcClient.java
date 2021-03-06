package com.welljay.easyrpc.client;

import com.welljay.easyrpc.client.future.FutureHolder;
import com.welljay.easyrpc.client.future.RpcFuture;
import com.welljay.easyrpc.serializer.RpcDecoder;
import com.welljay.easyrpc.serializer.RpcEncoder;
import com.welljay.easyrpc.server.RpcRequest;
import com.welljay.easyrpc.server.RpcResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;

/**
 * @author wenjie
 * @date 2018/4/20 0020 13:48
 */
public class RpcClient {

    private static final Integer THREADS_NUM = Runtime.getRuntime().availableProcessors() + 1;
    private String host;
    private int port;
    private Bootstrap bootstrap;
    private Channel channel;
    private EventLoopGroup workerGroup;

    public RpcClient(String host, int port) {
        this.host = host;
        this.port = port;
        init();
    }

    public void init() {
        workerGroup = new NioEventLoopGroup(THREADS_NUM);
        bootstrap = new Bootstrap();
        bootstrap.group(workerGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new RpcEncoder(RpcRequest.class))
                        .addLast(new RpcDecoder(RpcResponse.class))
                        .addLast(new RpcClientHandler());
            }
        });
    }

    public void connect() {
        ChannelFuture f = null;
        try {
            f = bootstrap.connect(host, port).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.channel = f.channel();
    }

    public void close() {
        try {
            this.channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public synchronized Object send(RpcRequest rpcRequest) {
        ChannelFuture channelFuture = channel.writeAndFlush(rpcRequest);
        RpcFuture rpcFuture = new RpcFuture(channelFuture.channel().eventLoop());
        channelFuture.addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                channel.closeFuture().addListener((ChannelFutureListener) closefuture -> {
                    System.out.println("stop client");
                });
                FutureHolder.registerFuture(rpcRequest.getRequestId(), rpcFuture);
            } else {
                rpcFuture.tryFailure(future.cause());
            }
        });
        RpcResponse rpcResponse = null;
        try {
            //没用listener和getNow的方式是因为客户端是同步的，同时简便实现
            rpcResponse = rpcFuture.get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (rpcResponse.getResult() != null) {
            return rpcResponse.getResult();
        } else {
            return null;
        }
    }

}
