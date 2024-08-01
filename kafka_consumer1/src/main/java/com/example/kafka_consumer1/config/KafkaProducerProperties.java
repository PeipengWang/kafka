package com.example.kafka_consumer1.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
@ConfigurationProperties("mq.kafka.producer")
public class KafkaProducerProperties {
    /**
     * 其他配置
     */
    private Map<String, String> other;
    /**
     * 集群地址
     */
    private String clusters;
    /**
     * 0	        Producer 往集群发送数据不需要等到集群的返回，不确保消息发送成功。安全性最低但是效率最高。
     * 1	        Producer 往集群发送数据只要 Leader 应答就可以发送下一条，只确保 Leader 接收成功。
     * -1 或 all    Producer 往集群发送数据需要所有的ISR Follower 都完成从 Leader 的同步才会发送下一条，
     *              确保 Leader 发送成功和所有的副本都成功接收。安全性最高，但是效率最低。
     */
    private String acks = "all";
    /**
     * 重试次数
     */
    private int retries = 0;
    /**
     * 将同分区数据合并为一个 batch 发送， 单位 bytes
     */
    private String batchSize = "16384";
    /**
     * 发送延迟时间，当 batch 未满 batchSize 时，延迟 lingerMs 发送 batch
     * 当数据达到 batchSize 将会被立即发送
     */
    private String lingerMs = "1";
    /**
     * 缓冲大小，当缓冲满时，max.block.ms 会控制 send() && partitionsFor() 阻塞时长
     */
    private String bufferMemory = "33554432";
    /**
     * bytes
     */
    private String maxRequestSize = "5048576";
    /**
     * 压缩方式 none, gzip, snappy, lz4, or zstd.
     */
    private String compressionType = "gzip";
    /**
     * key序列化方式，实现 org.apache.kafka.common.serialization.StringSerializer 的类
     */
    private String keySerializerClass = "org.apache.kafka.common.serialization.StringSerializer";
    /**
     * value序列化方式，实现 org.apache.kafka.common.serialization.StringSerializer 的类
     */
    private String valueSerializerClass = "org.apache.kafka.common.serialization.StringSerializer";
}
