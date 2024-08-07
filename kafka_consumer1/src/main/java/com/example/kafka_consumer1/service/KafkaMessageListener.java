package com.example.kafka_consumer1.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class KafkaMessageListener {

    @KafkaListener(topics = "mytopic",groupId = "my-group")
    public void applyStatusSync(String records) {
        System.out.println(records);
        // 反序列化 JSON 字符串为 EpduResultsWrapper 对象
//        EpduResultsWrapper wrapper = JSON.parseObject(records, EpduResultsWrapper.class);
//
//        // 打印 channel 和 epduResults 的大小
//        System.out.println("Channel: " + wrapper.getChannel());
//        System.out.println("Number of epduResults: " + wrapper.getEpduResults().size());



    }
//    private boolean processMessage(String records){
//
//            for (Map.Entry<String, ParameterConditionChecker> entry : checkers.entrySet()) {
//                String action = entry.getKey();
//                ParameterConditionChecker checker = entry.getValue();
//                if (checker.checkConditions(deviceParameters.get(deviceId))) {
//                    deviceStatus.put(deviceId, true);
//                    System.out.println("Device " + deviceId + " meets the conditions for action: " + action);
//                } else {
//                    deviceStatus.put(deviceId, false);
//                }
//            }
//
//            System.out.println("Device " + deviceId + " status: " + deviceStatus.get(deviceId));
//        }
//    }


}
