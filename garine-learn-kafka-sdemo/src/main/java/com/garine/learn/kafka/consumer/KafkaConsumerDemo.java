package com.garine.learn.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerDemo implements Runnable{
    private final KafkaConsumer kafkaConsumer;

    public KafkaConsumerDemo(String topic) {
        Properties properties=new Properties();
        //连接broker集群地址，多个broker逗号隔开
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.0.15:9092");
        //配置消费者所属的分组id
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,"KafkaConsumerDemo");
        //是否自动ack
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"false");
        //若果是自动ack，那么自动ack的频率是多长
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"1000");
        //反序列化key
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.IntegerDeserializer");
        //反序列化value
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
        //消费者加入group时，消费offset设置策略，earliest重置offset为最早的偏移地址，latest重置ofsset为已经消费的最大偏移，none没有offset时抛异常
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        kafkaConsumer=new KafkaConsumer(properties);
        kafkaConsumer.subscribe(Collections.singletonList(topic));
    }

    @Override
    public void run() {
        while(true){
            //拉取消息，暂时不确认消息
            ConsumerRecords<Integer,String> consumerRecord=kafkaConsumer.poll(1000);
            for(ConsumerRecord record:consumerRecord){
                System.out.println("-------------message receive:"+record.value());
                kafkaConsumer.commitAsync();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new KafkaConsumerDemo("test")).start();
    }
}
