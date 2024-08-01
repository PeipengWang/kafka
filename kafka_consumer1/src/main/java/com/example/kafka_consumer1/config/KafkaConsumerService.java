//package com.example.kafka_consumer1.config;
//
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.support.KafkaHeaders;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.stereotype.Service;
//
//import java.nio.charset.StandardCharsets;
//
//@Service
//public class KafkaConsumerService {
//
//
//    @KafkaListener(topics = "my-topic", groupId = "my-group")
//    public void listen(ConsumerRecord<String, byte[]> message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
//        byte[] value = message.value();
//        String messageStr = new String(value, StandardCharsets.UTF_8);
//        System.out.println("Received message from topic " + topic + ": " + messageStr);
//    }
//}
//
//
