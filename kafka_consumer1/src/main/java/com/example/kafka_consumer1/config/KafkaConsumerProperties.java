package com.example.kafka_consumer1.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "mq.kafka.consumer")
public class KafkaConsumerProperties {
    private Map<String, String> other;
    private String clusters ;//通过yaml的配置注入
    private String groupId ;
    private boolean enableAutoCommit = true;//是否自动提交
    private String sessionTimeoutMs = "120000";
    private String requestTimeoutMs = "160000";
    private String fetchMaxWaitMs = "5000";
    private String autoOffsetReset = "earliest";
    private String maxPollRecords = "10";//批量拉取消息数量
    private String maxPartitionFetch = "5048576";
    private String keyDeserializerClass = "org.apache.kafka.common.serialization.StringDeserializer";
    private String valueDeserializerClass = "org.apache.kafka.common.serialization.StringDeserializer";
}
