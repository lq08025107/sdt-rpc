package com.sdt.rpc.client;

import com.sdt.rpc.common.bean.RpcRequest;
import com.sdt.rpc.common.bean.RpcResponse;
import com.sdt.rpc.common.codec.RpcDecoder;
import com.sdt.rpc.common.codec.RpcEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RpcClient extends SimpleChannelInboundHandler<RpcResponse>{
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcClient.class);

    private final String host;

    private final int port;

    private RpcResponse rpcResponse;

    public RpcClient(String host, int port){
        this.host = host;
        this.port = port;
    }


    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponse response) throws Exception {
        this.rpcResponse = response;
    }

    public RpcResponse send(RpcRequest request) throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel channel) throws Exception {
                    ChannelPipeline pipline = channel.pipeline();
                    pipline.addLast(new RpcEncoder(RpcRequest.class));
                    pipline.addLast(new RpcDecoder(RpcResponse.class));
                    pipline.addLast(RpcClient.this);
                }
            });
            bootstrap.option(ChannelOption.TCP_NODELAY, true);
            ChannelFuture future = bootstrap.connect(host, port).sync();

            Channel channel = future.channel();
            channel.writeAndFlush(request).sync();
            channel.closeFuture().sync();

            return rpcResponse;
        } finally {
            group.shutdownGracefully();
        }
    }
}
