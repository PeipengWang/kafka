package com.example.kafka_consumer1.Entity;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EpduResultsWrapper wrapper = getEpduResultsWrapper(); // 假设这是获取对象的方法
        
        // 使用Stream API遍历所有的EpduResult和ParameterResult
        wrapper.getEpduResults().stream()
            .flatMap(epduResult -> epduResult.getParameterResults().stream())
            .forEach(parameterResult -> {
                // 对parameterResult进行操作
                System.out.println("Code: " + parameterResult.getCode());
                System.out.println("Name: " + parameterResult.getName());
                // 添加其他操作
            });
        wrapper.getEpduResults().stream()
                .flatMap(epduResult -> epduResult.getParameterResults().stream())
                .forEach(parameterResult -> {
                    System.out.println();
                });
    }
    
    private static EpduResultsWrapper getEpduResultsWrapper() {
        // 实现获取EpduResultsWrapper对象的逻辑
        return new EpduResultsWrapper();
    }
}
