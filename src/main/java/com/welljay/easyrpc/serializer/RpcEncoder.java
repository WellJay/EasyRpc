package com.welljay.easyrpc.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.ByteArrayOutputStream;

/**
 * @author wenjie
 * @date 2018/4/20 0020 16:49
 */
public class RpcEncoder extends MessageToByteEncoder {

    public static final int BUFFER_SIZE = 4096;
    private final Kryo kryo;
    private final Output output;

    public RpcEncoder(Class<?> genericClass) {
        kryo = new Kryo();
        kryo.register(genericClass);
        output = new Output(BUFFER_SIZE);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();

        output.setOutputStream(outStream);

        kryo.writeClassAndObject(output, msg);
        output.flush();

        byte[] outArray = outStream.toByteArray();
        out.writeShort(outArray.length);
        out.writeBytes(outArray);
    }

}
