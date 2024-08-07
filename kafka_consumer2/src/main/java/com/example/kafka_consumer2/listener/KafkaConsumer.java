package com.example.kafka_consumer2.listener;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class KafkaConsumer {



    @KafkaListener(topics = "mytopic",groupId = "my-group")
    public void topicTest(ConsumerRecord<?, ?> record,  @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional<?> message = Optional.ofNullable(record.value());
        if (message.isPresent()) { // 包含非空值，则执行
            Object msg = message.get();
            System.out.println("topic_test 消费了： Topic:" + topic + ",Message:" + msg);
        }
    }

}
