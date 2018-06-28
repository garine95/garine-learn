/*
 * Copyright (c) 2018 4PX Information Technology Co.,Ltd. All rights reserved.
 */
package com.garine.learn.common.mq.service;


/**
 * 消息发送接口
 */
public interface MessageSender {

    void send(String queueName, Object message);

    void send(String queueName, Object message, String messageId);

}
