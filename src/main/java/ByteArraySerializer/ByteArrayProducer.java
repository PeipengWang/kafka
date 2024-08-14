package ByteArraySerializer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;

import java.util.Properties;

public class ByteArrayProducer {

    public static void main(String[] args) {
        // 设置 Kafka 生产者的配置属性
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "43.143.251.77:9092"); // Kafka 服务器地址
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class.getName());

        // 创建 Kafka 生产者
        KafkaProducer<byte[], byte[]> producer = new KafkaProducer<>(props);

        // 构建要发送的消息
        String topic = "mytopic";
        byte[] key = "key".getBytes();
        byte[] value = "Hello, Kafka!".getBytes();

        // 发送消息
        producer.send(new ProducerRecord<>(topic, key, value));

        // 关闭生产者
        producer.close();
    }
}
