//package com.example.kafka_consumer1.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping(value = "/product")
//public class ProductController {
//
//    @Value("${mq.kafka.producer.topic}")
//    private String topic;
//
//    @Autowired
//    private KafkaTemplate<Integer, String> kafkaTemplate;
//
//    @RequestMapping(value = "/sendMsg")
//    public String sendMsg(){
//        String msg = "发送kafka消息";
//        kafkaTemplate.send(topic,msg);
//        return msg;
//    }
//}
