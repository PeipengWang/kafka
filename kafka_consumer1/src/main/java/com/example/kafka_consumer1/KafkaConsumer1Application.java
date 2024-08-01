package com.example.kafka_consumer1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class KafkaConsumer1Application {

    public static void main(String[] args) {
        SpringApplication.run(KafkaConsumer1Application.class, args);
    }

}
