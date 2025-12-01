package com.wpp.kafka_consumer_by_myself.listener;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerListener {


    /**
     * kafka消息处理类
     *
     * @param consumerRecord
     */
    public void listen(ConsumerRecord<String, String> consumerRecord) {
        String value = (String) consumerRecord.value();
        System.out.println("接收到一条消息：" + value);
    }
}
