package ReblanceTest;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.HashMap;
import java.util.Map;

public class KafkaProducerCycle {
    public static void main(String[] args) throws InterruptedException {
        while (true){
            Thread.sleep(10000);
            // 配置属性集合
            Map<String, Object> configMap = new HashMap<>();
            //  配置属性：Kafka服务器集群地址
            configMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "159.75.251.138:9092,43.143.251.77:9092,152.136.246.11:9092");
            //  配置属性：Kafka生产的数据为KV对，所以在生产数据进行传输前需要分别对K,V进行对应的序列化操作
            configMap.put(
                    ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                    "org.apache.kafka.common.serialization.StringSerializer");
            configMap.put(
                    ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                    "org.apache.kafka.common.serialization.StringSerializer");
            //  创建Kafka生产者对象，建立Kafka连接
            //    构造对象时，需要传递配置参数
            KafkaProducer<String, String> producer = new KafkaProducer<>(configMap);
            String json =  "{ \"channel\": \"TJS\", \"epduResults\": [ { \"apid\": 2721, \"extraInfo\": { \"success\": true }, \"parameterNum\": 2, \"parameterResults\": [ { \"code\": \"P_TF_WSBW\", \"enum\": false, \"name\": \"识别码\", \"origData\": \"0x0031\", \"overrun\": false, \"result\": \"0x0031\", \"value\": \"ox0031\", \"valueType\": \"OTHER\" }, { \"code\": \"P_TF_WSBW2\", \"enum\": false, \"name\": \"识别码2\", \"origData\": \"0x0031\", \"overrun\": false, \"result\": \"0x0031\", \"value\": \"ox0031\", \"valueType\": \"OTHER\" } ] }, { \"apid\": 2722, \"extraInfo\": { \"success\": true }, \"parameterNum\": 1, \"parameterResults\": [ { \"code\": \"P_TF_WSBW3\", \"enum\": false, \"name\": \"识别码3\", \"origData\": \"0x0031\", \"overrun\": false, \"result\": \"0x0031\", \"value\": \"ox0031\", \"valueType\": \"OTHER\" } ] } ] }";

            //   准备数据,定义泛型
            //    构造对象时需要传递 【Topic主题名称】，【Key】，【Value】三个参数
            ProducerRecord<String, String> record = new ProducerRecord<>(
                    "reblanceTopic", "key122", json
            );
            //        //  生产（发送）数据
        producer.send(record);
        //  关闭生产者连接
        producer.close();
        }



    }

}
