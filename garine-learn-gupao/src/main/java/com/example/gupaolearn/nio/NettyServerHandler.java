package com.example.gupaolearn.nio;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf=(ByteBuf)msg;
        byte [] data=new byte[buf.readableBytes()];
        buf.readBytes(data);
        System.out.println(new String(data));
        String responseMsg="收到"+System.getProperty("line.separator");
        ByteBuf sendMsg= Unpooled.copiedBuffer(responseMsg.getBytes());
        ctx.writeAndFlush(sendMsg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}