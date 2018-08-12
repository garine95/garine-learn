package com.garine.consumer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSSessionPrefecthConsumer2 {
    public static void main(String[] args) throws JMSException, InterruptedException {
        //根据broker URL建立连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.0.15:61616?jms.optimizeAcknowledge=true&jms.optimizeAcknowledgeTimeOut=100000");
        //创建连接
        Connection connection = connectionFactory.createConnection();
        connection.start();
        //创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建队列（有则不创建）
        Destination destination = session.createQueue("garine-queue?consumer.prefetchSize=10");
        //创建生产者
        MessageConsumer consumer = session.createConsumer(destination);
        //接受文本消息，阻塞等待
        for (int i=1;i<=100;i++){
            TextMessage textMessage = (TextMessage) consumer.receive();
            System.out.println(textMessage.getText());
            Thread.sleep(2000);
        }
        session.close();
    }
}
