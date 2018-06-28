/*
 * Copyright (c) 2018 4PX Information Technology Co.,Ltd. All rights reserved.
 */
package com.garine.learn.common.mq.service.impl;


import com.garine.learn.common.mq.service.MessageSender;
import com.garine.learn.common.mq.util.WorkId;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessageSenderImpl implements MessageSender {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public void send(String queue, Object message) {
        if (Objects.isNull(message)) {
            throw new RuntimeException("消息不能为空！");
        }
        WorkId workId = new WorkId(10);
        long messageId = workId.nextId();
        log.info("发送消息：{}到队列：{},消息Id：{}", message, queue, messageId);
        rabbitTemplate.convertAndSend(queue, message, (Message m) -> {
            m.getMessageProperties().setMessageId(messageId + "");
            return m;
        });
    }

    @Override
    public void send(String queueName, Object message, String messageId) {
        if (Objects.isNull(message)) {
            throw new RuntimeException("消息不能为空！");
        }
        log.info("发送消息：{}到队列：{},消息Id：{}", message, queueName, messageId);
        rabbitTemplate.convertAndSend(queueName, message, (Message m) -> {
            m.getMessageProperties().setMessageId(messageId + "");
            return m;
        });
    }
}
