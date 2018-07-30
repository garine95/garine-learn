package com.garine.producer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSCommonProducer {
    public static void main(String[] args) throws JMSException {
        //根据broker URL建立连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.0.15:61616");
        //创建连接
        Connection connection = connectionFactory.createConnection();
        //创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建队列（有则不创建）
        Destination destination = session.createQueue("garine-queue");
        //创建生产者
        MessageProducer producer = session.createProducer(destination);
        //创建文本消息，有多种消息类型
        TextMessage textMessage = session.createTextMessage("Hello garine");
        //发送消息
        producer.send(textMessage);
        System.out.println("over");
    }
}
