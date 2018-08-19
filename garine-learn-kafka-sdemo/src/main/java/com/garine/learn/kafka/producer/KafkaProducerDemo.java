package com.garine.learn.kafka.producer;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaProducerDemo extends Thread{
    private KafkaProducer<Integer, String> kafkaProducer;

    private String topic;

    private boolean isAsync;
    public KafkaProducerDemo(String topic, boolean isAysnc) {
        //配置kafka生产者的属性配置
        Properties properties = new Properties();
        //集群broker地址，多个broker地址逗号隔开
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.0.15:9092");
        //设置生产者id
        properties.put(ProducerConfig.CLIENT_ID_CONFIG,"KafkaProducerDemo");
        //设置发送消息ack模式
        properties.put(ProducerConfig.ACKS_CONFIG,"-1");
        //key序列化类
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.IntegerSerializer");
        //value序列化类
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");
        //设置批量发送消息的size
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 5);
        //延迟发送的时间，延迟时间内的消息一起发送到broker'
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 1000);
        //每次请求最大的字节数
        properties.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, 1024);
        kafkaProducer=new KafkaProducer<Integer, String>(properties);
        this.topic=topic;
        this.isAsync=isAysnc;
    }

    public KafkaProducerDemo(){

    }
    @Override
    public void run() {
        int count = 50;
        if (isAsync){
            do {
                kafkaProducer.send(new ProducerRecord<Integer, String>(topic, count,"isAsyncSend" + count), new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                        //异步发送回调函数，异步发送过程是类似队列消费过程，先将消息放到列表，然后有一个线程扫描这个列表，发现有消息则进行发送消费
                        if(recordMetadata!=null){
                            System.out.println("分区"+recordMetadata.partition()+
                                            "\n 偏移"+recordMetadata.offset());
                        }
                    }
                });
                count--;
            }while (count >0);
        }else {
            //同步发送消息,get是阻塞进行的
            do {
                try {
                    RecordMetadata recordMetadata = kafkaProducer.send(new ProducerRecord<Integer, String>(topic, count,"isAsyncSend" + count)).get();
                    System.out.println("分区"+recordMetadata.partition()+
                            "\n 偏移"+recordMetadata.offset());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                count--;
            }while (count >0);
        }
    }

    public void init() {

    }

    public static void main(String[] args) throws InterruptedException {
        KafkaProducerDemo kafkaProducerDemo = new KafkaProducerDemo("test", true);
        kafkaProducerDemo.start();
        Thread.sleep(10000);
    }
}

