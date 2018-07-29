package com.garine.producer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSCommonProducer {
    public static void main(String[] args) throws JMSException {
        //根据broker URL建立连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.0.15:61616");
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("garine-queue");
        MessageProducer producer = session.createProducer(destination);
        TextMessage textMessage = session.createTextMessage("Hello garine");
        producer.send(textMessage);
        System.out.println("------------------over");
    }
}
