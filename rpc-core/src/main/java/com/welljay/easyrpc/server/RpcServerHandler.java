package com.welljay.easyrpc.server;

import com.welljay.easyrpc.annotation.RpcService;
import com.welljay.easyrpc.util.AnnoManageUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wenjie
 * @date 2018/4/20 0020 11:39
 */

/**
 * 处理rpc
 */
@ChannelHandler.Sharable
public class RpcServerHandler extends ChannelInboundHandlerAdapter {


    private static final Map<String, Object> CLASS_MAP = new HashMap<>();

    public RpcServerHandler(String backPackage) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        List<Class<?>> classes = AnnoManageUtil.getPackageController(backPackage, RpcService.class);
        for (Class<?> aClass : classes) {
            CLASS_MAP.put(aClass.getInterfaces()[0].getSimpleName(), Class.forName(aClass.getName()).newInstance());
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcRequest request = (RpcRequest) msg;
        RpcResponse response = handle(request);
        ctx.writeAndFlush(response);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 出现异常时关闭连接。
        cause.printStackTrace();
        ctx.close();
    }


    private RpcResponse handle(RpcRequest request) {

        RpcResponse rpcResponse = new RpcResponse();
        rpcResponse.setRequestId(request.getRequestId());

        String className = request.getClassName();
        String methodName = request.getMethodName();

        Object clazz = CLASS_MAP.get(className);

        FastClass serviceFastClass = FastClass.create(clazz.getClass());
        FastMethod method = serviceFastClass.getMethod(methodName, request.getParameterTypes());
        Object result = null;
        try {
            result = method.invoke(clazz, request.getParameters());
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        rpcResponse.setResult(result);

        return rpcResponse;
    }
}
