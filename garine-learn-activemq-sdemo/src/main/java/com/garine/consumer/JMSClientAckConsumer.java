package com.garine.consumer;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.broker.region.policy.RedeliveryPolicyMap;
import org.apache.activemq.command.ActiveMQDestination;

import javax.jms.*;

public class JMSClientAckConsumer {
    public static void main(String[] args) throws JMSException {
        //根据broker URL建立连接工厂
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.0.15:61616");
        //创建连接
        ActiveMQConnection connection = (ActiveMQConnection) connectionFactory.createConnection();
        connection.start();
        //创建会话
        Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
        //重发策略
        RedeliveryPolicy queuePolicy = new RedeliveryPolicy();
        queuePolicy.setInitialRedeliveryDelay(0);
        queuePolicy.setRedeliveryDelay(20000);
        queuePolicy.setUseExponentialBackOff(false);
        queuePolicy.setMaximumRedeliveries(2);

        //创建队列（有则不创建）
        Destination destination = session.createQueue("garine-queue");

        RedeliveryPolicyMap map = connection.getRedeliveryPolicyMap();
        map.put((ActiveMQDestination) destination, queuePolicy);


        session.createConsumer(destination).setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(textMessage.getText());
                    session.rollback();
                    //session.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
