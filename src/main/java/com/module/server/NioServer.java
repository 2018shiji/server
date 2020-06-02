package com.module.server;

import com.module.server.mBeanInfo.ServerAttribute;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 非阻塞式IO的服务端
 * Netty Server
 */
@Component
public class NioServer {

    @Autowired
    @Qualifier("serverMBean")
    private ServerAttribute serverAttribute;
    private ServerBootstrap serverBootstrap;

    /** to be module import **/
    //@Autowired private ITaskDispatcher taskDispatcher;
    //@Autowired private ITaskHandler taskHandler;

    public void startNioServer(){
        EventLoopGroup connGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            buildBootstrap(connGroup, workerGroup);
            ChannelFuture future =
                    serverBootstrap.bind(serverAttribute.getPort());
            future.sync();

            future.channel().closeFuture().sync();

        } catch(InterruptedException e) {
            System.out.println(e.getMessage());

        } finally {
            connGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    protected void buildBootstrap(EventLoopGroup connGroup, EventLoopGroup workerGroup) {
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(connGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        /** to be module import **/
                        //socketChannel.pipeline().addLast("handler", (ChannelHandler)taskHandler.getTaskHandler());
                    }
                });
    }

}
