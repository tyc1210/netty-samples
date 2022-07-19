package com.tyc.netty.book01;

import com.alibaba.fastjson.JSONObject;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;

public class NettyClient {

public static ChannelFuture channelFuture = null;

	static {
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.channel(NioSocketChannel.class);
		EventLoopGroup group = new NioEventLoopGroup(10);
		bootstrap.group(group);
		bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
		final ClientHandler handler = new ClientHandler();
		bootstrap .handler(new ChannelInitializer<NioSocketChannel>() {
			@Override
			protected void initChannel(NioSocketChannel ch)
					throws Exception {
				ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,
						0, 4, 0, 4));
				ch.pipeline().addLast(new StringDecoder());
				ch.pipeline().addLast(handler);
				ch.pipeline().addLast(new LengthFieldPrepender(4, false));
				ch.pipeline().addLast(new StringEncoder(Charset.forName("utf-8")));
			}
		});
		try {
			channelFuture = bootstrap.connect("127.0.0.1",8080).sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	public static Object sendRequest(RequestFuture request) throws Exception {
		try {
			String requestStr = JSONObject.toJSONString(request);
			channelFuture.channel().writeAndFlush(requestStr);
			Object result = request.get();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	public static void main(String[] args) throws Exception {
		for(int i=0;i<100;i++) {
			RequestFuture request = new RequestFuture();
			request.setRequest(i);
			Object result = sendRequest(request);
			System.out.println(result);
		}
	}
}
