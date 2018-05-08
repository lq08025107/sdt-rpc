package com.sdt.rpc.server;

import com.sdt.rpc.common.bean.RpcRequest;
import com.sdt.rpc.common.bean.RpcResponse;
import com.sdt.rpc.common.util.StringUtil;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class RpcServerHandler extends SimpleChannelInboundHandler<RpcRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcServerHandler.class);

    private final Map<String, Object> handlerMap;

    public RpcServerHandler(Map<String, Object> handlerMap){this.handlerMap = handlerMap;}

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcRequest rpcRequest) throws Exception {
        RpcResponse response = new RpcResponse();
        response.setRequestId(rpcRequest.getRequestId());
        try{
            Object result = handle(rpcRequest);
            response.setResult(result);
        } catch (Exception e){
            LOGGER.error("handle result failed", e);
            response.setException(e);
        }

        channelHandlerContext.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private Object handle(RpcRequest request) throws Exception {
        String serviceName = request.getInterfaceName();
        String serviceVersion = request.getServiceVersion();
        if(StringUtil.isNotEmpty(serviceVersion)){
            serviceName += "-" + serviceVersion;
        }
        Object serviceBean = handlerMap.get(serviceName);
        if(serviceBean == null){
            throw new RuntimeException(String.format("can not find service bean by key: %s", serviceName));
        }


        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();

        //使用jdk自带反射
        Object o = serviceClass.newInstance();
        Method method = serviceClass.getMethod(methodName, parameterTypes);
        return method.invoke(o, parameters);
        //使用cglib
//        FastClass serviceFastClass = FastClass.create(serviceClass);
//        FastMethod serviceMethod = serviceFastClass.getMethod(methodName, parameterTypes);
//        return serviceMethod.invoke(serviceBean, parameters);
    }
}
