package com.garine.learn.kafka;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 * 自定义消息发送到哪个分区的策略
 */
public class OwnPartitionStrategy implements Partitioner{
    private Random random = new Random();
    @Override
    public int partition(String topic, Object key, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
        List<PartitionInfo> partitionInfos =  cluster.partitionsForTopic(topic);
        int toPar;
        if (Objects.isNull(key)){
            toPar =  random.nextInt(partitionInfos.size());
        }else {
            toPar = key.hashCode() % partitionInfos.size();
        }
        System.out.println(key + "即将发往分区:" + toPar);
        return toPar;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
