package com.garine.learn.common.mq.constant;

/**
 * @author wugx
 * @date 2018/4/9 17:22
 */
public interface ExchangeConst {
    /**
     * 配置默认交换机
     **/
     String DEFAULT_EXCHANGE = "fb4.exchange";
    /**
     * 配置死信交换机
     **/
     String DEAD_EXCHANGE = "fb4.dead.exchange";
    /**
     * DLX QUEUE
     **/
     String DEAD_LETTER_QUEUE_NAME = ".dead";
    /**
     * X_MESSAGE_TTL
     * */
     Long X_MESSAGE_TTL = 60000L;
}
