package com.welljay.easyrpc.client;

import com.welljay.easyrpc.serializer.RpcDecoder;
import com.welljay.easyrpc.serializer.RpcEncoder;
import com.welljay.easyrpc.server.RpcRequest;
import com.welljay.easyrpc.server.RpcResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author wenjie
 * @date 2018/4/20 0020 13:48
 */
public class RpcClient {

    public static void main(String[] args) throws Exception {
        String host = "127.0.0.1";
        int port = 9000;
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new RpcEncoder(RpcRequest.class))
                            .addLast(new RpcDecoder(RpcResponse.class))
                            .addLast(new RpcClientHandler());
                }
            });

            ChannelFuture f = b.connect(host, port).sync();

            f.channel().closeFuture().sync();


        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
