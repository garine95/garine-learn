package com.garine.producer;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.broker.region.policy.RedeliveryPolicyMap;
import org.apache.activemq.command.ActiveMQDestination;

import javax.jms.*;

public class JMSCommonProducer {
    public static void main(String[] args) throws JMSException {
        //根据broker URL建立连接工厂
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.0.15:61616");
        //创建连接
        ActiveMQConnection connection = (ActiveMQConnection) connectionFactory.createConnection();
        connection.start();
        //创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //重发策略
        RedeliveryPolicy queuePolicy = new RedeliveryPolicy();
        queuePolicy.setInitialRedeliveryDelay(0);
        queuePolicy.setRedeliveryDelay(1000);
        queuePolicy.setUseExponentialBackOff(false);
        queuePolicy.setMaximumRedeliveries(2);

        //创建队列（有则不创建）
        Destination destination = session.createQueue("garine-queue");

        RedeliveryPolicyMap map = connection.getRedeliveryPolicyMap();
        map.put((ActiveMQDestination) destination, queuePolicy);

        //创建生产者
        MessageProducer producer = session.createProducer(destination);
        //创建文本消息，有多种消息类型
        TextMessage textMessage = session.createTextMessage("Hello garine");
        //发送消息
        producer.send(textMessage);
        System.out.println("over");
        session.close();
    }
}
