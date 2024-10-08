import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class KafkaConsumerTest {

  public static void main(String[] args) {
    // 配置属性集合
    Map<String, Object> configMap = new HashMap<String, Object>();
   //  配置属性：Kafka集群地址
   configMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "43.143.251.77:9092");
   // 配置属性: Kafka传输的数据为KV对，所以需要对获取的数据分别进行反序列化
   configMap.put(
       ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
       "org.apache.kafka.common.serialization.StringDeserializer");
   configMap.put(
       ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
       "org.apache.kafka.common.serialization.StringDeserializer");
   //  配置属性: 读取数据的位置 ，取值为earliest（最早），latest（最晚）
   configMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
   // 配置属性: 消费者组
    configMap.put("group.id", "atguigu");
   // 配置属性: 自动提交偏移量
   configMap.put("enable.auto.commit", "true");
   KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(configMap);
   //  消费者订阅指定主题的数据
   consumer.subscribe(Collections.singletonList("mytopic"));
   while (true) {
     // 每隔100毫秒，抓取一次数据
     ConsumerRecords<String, String> records =
             consumer.poll(Duration.ofMillis(100));
     // 打印抓取的数据
     for (ConsumerRecord<String, String> record : records) {
       System.out.println("K = " + record.key() + ", V = " + record.value());
     }
   }
  }

}