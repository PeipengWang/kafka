package com.example.kafka_consumer1.service;

import com.alibaba.fastjson.JSON;
import com.example.kafka_consumer1.Entity.EpduResultsWrapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageListener {

    @KafkaListener(topics = "my-topic",groupId = "my-group")
    public void applyStatusSync(String records) {
        System.out.println(records);
        // 反序列化 JSON 字符串为 EpduResultsWrapper 对象
        EpduResultsWrapper wrapper = JSON.parseObject(records, EpduResultsWrapper.class);

        // 打印 channel 和 epduResults 的大小
        System.out.println("Channel: " + wrapper.getChannel());
        System.out.println("Number of epduResults: " + wrapper.getEpduResults().size());
    }
//    手动提交偏移量
//    @KafkaListener(topics = "${mq.kafka.topic}",groupId = "${mq.kafka.consumer.groupId}")
//    public void applyStatusSync(ConsumerRecords<Integer, String> records, Acknowledgment ack) {
//        log.info("kafka poll ：count:{}", records.count());
//        for (ConsumerRecord<Integer, String> record : records) {
//            log.info("接收到的kafka 数据 {}", record.value());
//        }
//        ack.acknowledge();
//    }

}
