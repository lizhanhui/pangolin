package com.yeahmobi.pangolin;

import com.yeahmobi.pangolin.impl.ProxyHandler;
import com.yeahmobi.pangolin.impl.ThreadFactoryImpl;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class TestServer implements Server {

    private ServerConfig config;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public TestServer(ServerConfig config) {
        this.config = config;
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors(), new ThreadFactoryImpl("WorkerThread_"));
    }

    public void start() {
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, config.getConnectTimeout())
                    .childOption(ChannelOption.SO_SNDBUF, config.getSendBufferSize())
                    .childOption(ChannelOption.RCVBUF_ALLOCATOR,
                            new AdaptiveRecvByteBufAllocator(config.getMaxReceiveBufferSize() >> 2, config.getMaxReceiveBufferSize() >> 1, config.getMaxReceiveBufferSize()))
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new HttpServerCodec())
                                    .addLast(new ProxyHandler());
                        }
                    });

            ChannelFuture channelFuture = serverBootstrap.bind(config.getPort()).sync();
            System.out.println("Listening port: " + config.getPort());
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public void stop() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

}
