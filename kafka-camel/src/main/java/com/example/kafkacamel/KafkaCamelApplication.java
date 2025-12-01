package com.example.kafkacamel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class KafkaCamelApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaCamelApplication.class, args);
    }

}
