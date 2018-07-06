package com.garine.learn.myrpc.transport;

import com.garine.learn.myrpc.Request;

import java.io.IOException;

public interface Transport {

    Object sendMessage(Request request);

    Request receiveMessage(Object...ins) throws Exception;

}
