//package com.example.kafkacamel.component;
//
//import org.apache.camel.builder.RouteBuilder;
//import org.springframework.stereotype.Component;
//
//@Component
//public class KafkaCamelRoute extends RouteBuilder {
//    @Override
//    public void configure() throws Exception {
//        from("kafka:mytopic?brokers=43.143.251.77:9092")
//                .process(exchange -> {
//                    String message = exchange.getIn().getBody(String.class);
//                    // 处理消息的逻辑
//                    System.out.println("Received message: " + message);
//                });
//    }
//}
