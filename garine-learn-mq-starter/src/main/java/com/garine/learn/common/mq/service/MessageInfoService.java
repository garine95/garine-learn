package com.garine.learn.common.mq.service;

import com.garine.learn.common.mq.model.MessageInfo;

/**
 * 消息接口
 *
 * @author wugx
 * @date 2018/6/6 11:24
 */
public interface MessageInfoService {

    /**
     * 保存消息
     * @param messageInfo
     */
    void save(MessageInfo messageInfo);

    /**
     * 判断消息是否消费过
     * @param msgId
     * @return
     */
    boolean isExist(String msgId);
}
