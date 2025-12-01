package com.wpp.kafka_consumer_by_myself.config;

import com.wpp.kafka_consumer_by_myself.listener.KafkaConsumerListener;
import com.wpp.kafka_consumer_by_myself.listener.KafkaListenerJob;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Collections;
import java.util.Properties;

@Configuration
public class KafkaConfigNew {

    @Autowired
    private Environment environment;

    @Autowired
    private KafkaConsumerListener kafkaConsumerListener;

    public static KafkaConsumer<String, String> kafkaConsumer;

    @Bean
    public void loadKafkaConfig() {
        Properties p = new Properties();
        p.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, environment.getProperty("bootstrap.servers"));
        p.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, environment.getProperty("key.deserializer"));
        p.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, environment.getProperty("value.deserializer"));
        p.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, environment.getProperty("enable.auto.commit"));
        p.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, environment.getProperty("auto.commit.interval.ms"));
        p.put(ConsumerConfig.GROUP_ID_CONFIG, environment.getProperty("group.id"));

        kafkaConsumer = new KafkaConsumer<String, String>(p);
        kafkaConsumer.subscribe(Collections.singletonList(environment.getProperty("kafkaTopicName")));// 订阅消息
        System.out.println("消息订阅成功！kafka配置：" + p.toString());
        //启动消息监听线程
        KafkaListenerJob kafkaListenerJob = new KafkaListenerJob(kafkaConsumerListener);
        Thread t = new Thread(kafkaListenerJob);
        t.start();
    }
}