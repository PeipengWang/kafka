package com.example.kafkacamel.component;

import com.example.kafkacamel.processor.JudgmentAndParamProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;  
  
@Component  
public class KafkaRoute extends RouteBuilder {  
    @Override  
    public void configure() throws Exception {  
        from("kafka:mytopic?brokers=43.143.251.77:9092&groupId=mygroup")
            .process(new JudgmentAndParamProcessor()).end();
    }  
}