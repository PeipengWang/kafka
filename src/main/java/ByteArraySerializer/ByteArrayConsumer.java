package ByteArraySerializer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ByteArrayConsumer {

    public static void main(String[] args) {
        // 设置 Kafka 消费者的配置属性
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "43.143.251.77:9092"); // Kafka 服务器地址
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());

        // 创建 Kafka 消费者
        KafkaConsumer<byte[], byte[]> consumer = new KafkaConsumer<>(props);

        // 订阅主题
        consumer.subscribe(Collections.singletonList("mytopic"));

        // 拉取消息
        while (true) {
            ConsumerRecords<byte[], byte[]> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<byte[], byte[]> record : records) {
                String key = new String(record.key());
                String value = new String(record.value());
                System.out.printf("Received message: Key = %s, Value = %s%n", key, value);
            }
        }
    }
}
