package com.tyc.netty.book01;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;


/**
 * ServerHandler需要继承ChannelInboundHandlerAdapter，它是ChannelInboundHandler的子类，
 * 这跟Netty的处理数据流向有关。
 * 当NioEventLoop线程从Channel读取数据时，执行绑定在Channel的ChannelInboundHandler对象上，并执行其channelRead()方法
 */
// @Sharable 注解表示此 Handler 对所有 Channel 共享，无状态，注意多线程并发
@ChannelHandler.Sharable
@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {

    // 读取客户端数据
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RequestFuture req = JSONObject.parseObject(msg.toString(), RequestFuture.class);
        Long id = req.getId();
        log.info("请求信息为：{}",msg.toString());
        Response response = new Response();
        response.setId(id);
        response.setResult("服务器响应OK");
        ctx.channel().write(JSONObject.toJSONString(response));
        ctx.channel().unsafe().flush();
    }
}
