package ReblanceTest;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

public class ConsumerThread implements Runnable {
    private final String topic;
    private final String groupId;
    private final String consumerId;
    private volatile boolean running = true;

    public ConsumerThread(String topic, String groupId, String consumerId) {
        this.topic = topic;
        this.groupId = groupId;
        this.consumerId = consumerId;
    }

    @Override
    public void run() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "159.75.251.138:9092,43.143.251.77:9092,152.136.246.11:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        // 设置重平衡监听器
        consumer.subscribe(Collections.singletonList(topic), new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                System.out.println("Consumer " + consumerId + " - Partitions revoked: " + partitions);
            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                System.out.println("Consumer " + consumerId + " - Partitions assigned: " + partitions);
            }
        });

        try {
            while (running) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("Consumer %s consumed record with key %s and value %s from partition %d at offset %d%n",
                            consumerId, record.key(), record.value(), record.partition(), record.offset());
                }
            }
        } catch (Exception e) {
            System.out.println("Consumer " + consumerId + " interrupted.");
        } finally {
            consumer.close();
            System.out.println("Consumer " + consumerId + " closed.");
        }
    }

    public void shutdown() {
        running = false;
    }
}
