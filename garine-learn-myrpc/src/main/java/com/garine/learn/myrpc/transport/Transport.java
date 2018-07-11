package com.garine.learn.myrpc.transport;

import com.garine.learn.myrpc.Request;

import java.io.IOException;

/**
 * 通道接口
 * @author zhoujy
 */
public interface Transport {

    Object sendMessage(Request request);

    Request receiveMessage(Object...ins) throws Exception;

}
