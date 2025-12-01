package com.wpp.kafka_consumer_by_myself.listener;


import com.wpp.kafka_consumer_by_myself.config.KafkaConfigNew;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;

public class KafkaListenerJob implements Runnable {

    @Autowired
    private KafkaConsumerListener kafkaConsumerListener;

    //注入消息监听处理类
    public KafkaListenerJob(KafkaConsumerListener kafkaConsumerListener) {
        this.kafkaConsumerListener = kafkaConsumerListener;
    }

    @Override
    public void run() {
        System.out.println("kafka消息监听任务已启动！");
        //进行消息监听
        while (true) {
            ConsumerRecords<String, String> records = KafkaConfigNew.kafkaConsumer.poll(100);
            //log.info("poll数据：" + JSON.toJSONString(records));
            for (ConsumerRecord<String, String> record : records) {
                try {
                    kafkaConsumerListener.listen(record);
                } catch (Exception e) {
                    System.out.println("消息消费异常！");
                }
            }
        }
    }
}
