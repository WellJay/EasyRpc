package com.welljay.easyrpc.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.welljay.easyrpc.annotation.RpcService;
import com.welljay.easyrpc.util.AnnoManageUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import javax.annotation.PostConstruct;
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
public class RpcServerHandler extends ChannelInboundHandlerAdapter {


    private static final Map<String, Object> classMap = new HashMap<>();

    public RpcServerHandler() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        List<Class<?>> classes = AnnoManageUtil.getPackageController("com.welljay.easyrpc.service.impl", RpcService.class);
        for (Class<?> aClass : classes) {
            classMap.put(aClass.getName(), Class.forName(aClass.getName()).newInstance());
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcRequest request = JSON.parseObject((String) msg, RpcRequest.class);
        RpcResponse response = handle(request);
        ctx.writeAndFlush(JSON.toJSONString(response));
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

        Object clazz = classMap.get(className);

        FastClass serviceFastClass = FastClass.create(clazz.getClass());
        FastMethod method = serviceFastClass.getMethod(methodName, null);
        Object result = null;
        try {
            result = method.invoke(clazz, null);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        rpcResponse.setResult(result);

        return rpcResponse;
    }
}
