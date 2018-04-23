package com.welljay.easyrpc.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author wenjie
 * @date 2018/4/20 0020 17:42
 */
public class RpcDecoder extends ByteToMessageDecoder {

    private final Kryo kryo;
    private final Input input;

    public RpcDecoder(Class<?> genericClass) {
        kryo = new Kryo();
        kryo.register(genericClass);
        input = new Input();
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 2) {
            return;
        }
        in.markReaderIndex();

        int len = in.readUnsignedShort();

        if (in.readableBytes() < len) {
            in.resetReaderIndex();
            return;
        }

        byte[] buf = new byte[len];
        in.readBytes(buf);

        input.setBuffer(buf);

        Object object = kryo.readClassAndObject(input);
        out.add(object);
    }
}
