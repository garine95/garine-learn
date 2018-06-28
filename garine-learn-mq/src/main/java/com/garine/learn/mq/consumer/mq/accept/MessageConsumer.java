package com.garine.learn.mq.consumer.mq.accept;

import com.garine.learn.common.mq.annotation.MqReceiverAnnotation;
import com.garine.learn.common.mq.constant.QueueConstant;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageConsumer {

    @MqReceiverAnnotation(description = "消息处理")
    @RabbitListener(queues = QueueConstant.TEST_QUEUE,containerFactory = "myFactory")
    public void onMessage(@Payload String json, Channel channel, Message message) throws Exception {
        log.info(json);
    }
}
