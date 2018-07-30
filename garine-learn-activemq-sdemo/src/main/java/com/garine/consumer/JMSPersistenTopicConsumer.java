package com.garine.consumer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSPersistenTopicConsumer {
    public static void main(String[] args) throws JMSException {
        //根据broker URL建立连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.0.15:61616");
        //创建连接
        Connection connection = connectionFactory.createConnection();
        connection.setClientID("persistent-sub-id");
        connection.start();
        //创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建队列（有则不创建）
        Topic topic = session.createTopic("garine-topic");
        //创建消费者
        MessageConsumer consumer = session.createDurableSubscriber(topic, "persistent-sub-id");
        //接受文本消息，阻塞等待
        TextMessage textMessage = (TextMessage) consumer.receive();
        System.out.println(textMessage.getText());
        session.close();
    }
}
