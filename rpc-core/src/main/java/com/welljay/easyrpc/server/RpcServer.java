package com.welljay.easyrpc.server;

import com.welljay.easyrpc.serializer.RpcDecoder;
import com.welljay.easyrpc.serializer.RpcEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author wenjie
 * @date 2018/4/20 0020 13:36
 */
public class RpcServer {

    public static final int port = 9999;

    public static void start(String backPackage) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup(8);

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        private final RpcEncoder rpcEncoder = new RpcEncoder(RpcResponse.class);
                        private final RpcDecoder rpcDecoder = new RpcDecoder(RpcRequest.class);
                        private final RpcServerHandler rpcServerHandler = new RpcServerHandler(backPackage);

                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(rpcEncoder)
                                    .addLast(rpcDecoder)
                                    .addLast(rpcServerHandler);
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = bootstrap.bind(port).sync();

            System.out.println("server started...");

            f.channel().closeFuture().sync();

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}
