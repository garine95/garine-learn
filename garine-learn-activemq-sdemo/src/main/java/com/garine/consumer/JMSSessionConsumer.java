package com.garine.consumer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSSessionConsumer {
    public static void main(String[] args) throws JMSException {
        //根据broker URL建立连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.0.15:61616");
        //创建连接
        Connection connection = connectionFactory.createConnection();
        connection.start();
        //创建会话
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        //创建队列（有则不创建）
        Destination destination = session.createQueue("garine-queue");
        //创建生产者
        MessageConsumer consumer = session.createConsumer(destination);
        //接受文本消息，阻塞等待
        TextMessage textMessage = (TextMessage) consumer.receive();
        System.out.println(textMessage.getText());
        session.commit();
        session.close();
    }
}
