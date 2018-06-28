package com.garine.learn.common.mq.model;

import java.util.Date;

import lombok.Data;

/**
 * 消息实体
 *
 * @author wugx
 * @date 2018/6/6 11:22
 */
@Data
public class MessageInfo {

    private Long tid;

    /**
     * 消息id
     */
    private String msgId;

    /**
     * 消息状态
     */
    private String msgStatus;

    /**
     * 更新时间
     */
    private Date createTime;
}
