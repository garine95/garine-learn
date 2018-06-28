package com.garine.learn.common.mq.service.impl;

import com.garine.learn.common.mq.model.MessageInfo;
import com.garine.learn.common.mq.service.MessageInfoService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Types;

import javax.annotation.Resource;

/**
 * @author wugx
 * @date 2018/6/6 11:26
 */
@Service
public class MessageInfoServiceImpl implements MessageInfoService {
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(MessageInfo messageInfo) {
        jdbcTemplate.update("insert into message_info (msg_id,msg_status,create_time) values (?,?,?)", new Object[]{messageInfo.getMsgId(), StringUtils.trimToEmpty(messageInfo.getMsgStatus()), new java.util.Date()}, new int[]{Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP});
    }

    @Override
    public boolean isExist(String msgId) {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setMsgId(msgId);
        long count = jdbcTemplate.queryForObject("select count(*) from message_info where msg_id = '" + msgId + "';", Integer.class);
        return count > 0;
    }
}
