package com.example.kafkacamel.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class JudgmentAndParamProcessor implements Processor {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void process(Exchange exchange) throws Exception {
        // 假设从Kafka接收到的消息体是一个JSON字符串
        String jsonString = exchange.getIn().getBody(String.class);
        System.out.println(jsonString);
        // 使用Jackson将JSON字符串转换为JsonNode对象，以便可以进一步处理
        JsonNode jsonNode = objectMapper.readTree(jsonString);

        // 在这里，你可以根据需要对jsonNode进行各种操作
        // 例如，打印整个JSON字符串或访问特定的JSON字段

        // 打印整个JSON字符串
        System.out.println("Received JSON: " + jsonString);

        // 或者，如果你需要访问特定的字段
        // 假设JSON中有一个名为"name"的字段
        if (jsonNode.has("name")) {
            String name = jsonNode.get("name").asText();
            System.out.println("Name from JSON: " + name);
        }
        // 注意：这里并没有发送消息到Kafka，因为这不是处理器的典型职责。
        // 如果你需要发送消息到Kafka，你应该在Camel路由中配置一个Kafka生产者端点。
    }
}
