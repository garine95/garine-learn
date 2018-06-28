package com.garine.learn.common.mq.conf;

import com.google.common.collect.Lists;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import lombok.Data;
import lombok.Getter;

import static com.garine.learn.common.mq.constant.ExchangeConst.DEAD_EXCHANGE;
import static com.garine.learn.common.mq.constant.ExchangeConst.DEAD_LETTER_QUEUE_NAME;
import static com.garine.learn.common.mq.constant.ExchangeConst.DEFAULT_EXCHANGE;
import static com.garine.learn.common.mq.constant.ExchangeConst.X_MESSAGE_TTL;

/**
 * @author wugx
 * @date 2018/4/9 17:26
 */
@Data
public abstract class AbstractMqConfig {
    @Getter
    protected static Map<String, Queue> queueMap = new ConcurrentHashMap<>();
    @Resource
    private JdbcTemplate jdbcTemplate;


    protected void init(List<String> queueNames) {
        initDatabase();
        for (String queueName : queueNames) {
            String deadQueueName = queueName + DEAD_LETTER_QUEUE_NAME;
            Queue messageQueue = QueueBuilder.nonDurable(queueName)
                    .withArgument("x-dead-letter-exchange", DEAD_EXCHANGE)
                    .withArgument("x-dead-letter-routing-key", deadQueueName).build();
            Queue deadQueue = QueueBuilder.nonDurable(deadQueueName)
                    .withArgument("x-dead-letter-exchange", DEFAULT_EXCHANGE)
                    .withArgument("x-dead-letter-routing-key", queueName)
                    .withArgument("x-message-ttl", X_MESSAGE_TTL)
                    .build();
            queueMap.put(queueName, messageQueue);
            queueMap.put(deadQueueName, deadQueue);
        }
    }

    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(DEFAULT_EXCHANGE, false, false);
    }

    @Bean
    public DirectExchange deadExchange() {
        return new DirectExchange(DEAD_EXCHANGE, false, false);
    }

    @Bean
    public List<Queue> queues() {
        return Lists.newArrayList(queueMap.values());
    }

    @Bean
    public List<Binding> binding() {
        List<Binding> bindings = Lists.newArrayListWithCapacity(queueMap.size());
        for (Map.Entry<String, Queue> entry : queueMap.entrySet()) {
            DirectExchange directExchange = defaultExchange();
            String queueName = entry.getKey();
            if (queueName.contains(DEAD_LETTER_QUEUE_NAME)) {
                directExchange = deadExchange();
            }
            Binding binding = BindingBuilder.bind(entry.getValue()).to(directExchange).with(queueName);
            bindings.add(binding);
        }
        return bindings;
    }


    @Bean(name = "myFactory")
    public SimpleRabbitListenerContainerFactory myFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory, @Value("${fb4.rabbitmq.concurrency:2}") int concurrentConsumers) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setConcurrentConsumers(concurrentConsumers);
        return factory;
    }


    /**
     * 初始化数据库脚本
     */
    private void initDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS `message_info` ( " +
                "  `tid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '标识', " +
                "  `msg_id` varchar(100) NOT NULL DEFAULT '' COMMENT '事物业务ID', " +
                "  `msg_status` varchar(1) NOT NULL default '' COMMENT '状态' ," +
                "  `create_time` timestamp NULL DEFAULT NULL COMMENT '更新时间' ," +
                "  PRIMARY KEY (`tid`), " +
                "  UNIQUE KEY `msg_id` (`msg_id`) USING BTREE " +
                ") COMMENT='本地消息排重表'";
        jdbcTemplate.execute(sql);
    }

}
