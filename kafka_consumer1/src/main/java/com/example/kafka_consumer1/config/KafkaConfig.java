package com.example.kafka_consumer1.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {
    @Autowired
    private KafkaProducerProperties producerProperties;

    @Bean
    public ProducerFactory<Integer, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>(16);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producerProperties.getClusters());
        props.put(ProducerConfig.ACKS_CONFIG, producerProperties.getAcks());
        props.put(ProducerConfig.RETRIES_CONFIG, producerProperties.getRetries());
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, producerProperties.getBatchSize());
        props.put(ProducerConfig.LINGER_MS_CONFIG,  producerProperties.getLingerMs());
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, producerProperties.getBufferMemory());
        props.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, producerProperties.getMaxRequestSize());
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, producerProperties.getCompressionType());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, producerProperties.getKeySerializerClass());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, producerProperties.getValueSerializerClass());
        if (producerProperties.getOther() != null){
            props.putAll(producerProperties.getOther());
        }
        return props;
    }

    @Bean
    public KafkaTemplate<Integer, String> kafkaTemplate() {

        return new KafkaTemplate<>(producerFactory());
    }
}
