package com.welljay.easyrpc.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.DefaultSerializers;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.ByteArrayOutputStream;

/**
 * @author wenjie
 * @date 2018/4/20 0020 16:49
 */
public class RpcEncoder extends MessageToByteEncoder {

    private Kryo kryo;
    private Class<?> genericClass;
    public RpcEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
        kryo=new Kryo();
        kryo.register(genericClass);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        Output output = new Output(new ByteBufOutputStream(out));
        kryo.writeClassAndObject(output, msg);
        byte[] buffer = output.getBuffer();
        out.writeInt(buffer.length);
        out.writeBytes(buffer);
        output.close();
    }

}
