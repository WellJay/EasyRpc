package com.welljay.easyrpc.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.welljay.easyrpc.server.RpcRequest;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author wenjie
 * @date 2018/4/20 0020 17:42
 */
public class RpcDecoder extends ByteToMessageDecoder {

    private Kryo kryo;
    private Class<?> genericClass;

    public RpcDecoder(Class<?> genericClass) {
        this.genericClass = genericClass;
        kryo = new Kryo();
        kryo.register(genericClass);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Input input = new Input(new ByteBufInputStream(in));
        RpcRequest request = kryo.readObject(input, RpcRequest.class);
        out.add(request);
        input.close();
    }
}
