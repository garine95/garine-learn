package com.garine.learn.common.mq.aop;

import com.garine.learn.common.mq.annotation.MqReceiverAnnotation;
import com.garine.learn.common.mq.model.MessageInfo;
import com.garine.learn.common.mq.service.MessageInfoService;
import com.rabbitmq.client.Channel;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

/**
 * 任务消息接收AOP
 *
 * @author chyern
 * @date 2018/3/29
 */
@Slf4j
@Aspect
@Component
public class MqReceiverAspect {
    @Resource
    private MessageInfoService messageInfoService;

    @Pointcut("@annotation(com.garine.learn.common.mq.annotation.MqReceiverAnnotation)")
    public void taskReceiverManager() {
    }

    @Around("taskReceiverManager()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws ClassNotFoundException, IOException {
        Class<?> aClass = joinPoint.getTarget().getClass();
        Signature signature = joinPoint.getSignature();
        String targetName = aClass.getName();
        String methodName = signature.getName();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String desc;
        Object[] args = joinPoint.getArgs();
        Method method = Arrays.stream(methods).filter(m -> m.getName().equals(methodName)).findFirst().get();
        MqReceiverAnnotation annotation = method.getAnnotation(MqReceiverAnnotation.class);
        desc = annotation.description();

        Long deliveryTag;
        if (args.length < 2) {
            log.error("监听方法参数不正确：{}", method);
            return null;
        }
        Channel channel = (Channel) args[1];
        Message message = null;
        if (args.length == 3 && args[2] instanceof Message) {
            message = (Message) args[2];
            deliveryTag = message.getMessageProperties().getDeliveryTag();
        } else if (args.length == 3) {
            deliveryTag = (Long) args[2];
        } else {
            message = (Message) args[0];
            deliveryTag = message.getMessageProperties().getDeliveryTag();
        }
        log.info("接收mq消息{}:{}", desc, Objects.nonNull(message) ? message.toString() : args[0]);
        Object ret = null;
        try {
            String messageId = null;
            if (Objects.nonNull(message)) {
                messageId = message.getMessageProperties().getMessageId();
            }
            if (StringUtils.isBlank(messageId)) {
                log.error("消息msg:{}的messageId为空", Objects.nonNull(message) ? message.toString() : args[0]);
                throw new RuntimeException("messageId: 为空");
            }

            if (messageInfoService.isExist(messageId)) {
                log.error("消息：{}已消费", message.toString());
            }

            ret = joinPoint.proceed();

            MessageInfo messageInfo = new MessageInfo();
            messageInfo.setMsgId(messageId);
            messageInfoService.save(messageInfo);

            channel.basicAck(deliveryTag, false);
            log.info("处理mq消息成功");
        } catch (Throwable throwable) {
            log.error("处理{}失败:", desc);
            log.error("处理mq失败：", throwable);
            channel.basicReject(deliveryTag, false);
        }
        return ret;
    }
}
