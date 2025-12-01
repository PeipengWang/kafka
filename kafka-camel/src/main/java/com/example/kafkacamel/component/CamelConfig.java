//package com.example.kafkacamel.component;
//
//import org.apache.camel.CamelContext;
//import org.apache.camel.impl.DefaultCamelContext;
//import org.apache.camel.impl.engine.DefaultShutdownStrategy;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class CamelConfig {
//
//    @Bean
//    public CamelContext camelContext() {
//        DefaultCamelContext camelContext = new DefaultCamelContext();
//        DefaultShutdownStrategy shutdownStrategy = new DefaultShutdownStrategy();
//        shutdownStrategy.setTimeout(60);
//        camelContext.setShutdownStrategy(shutdownStrategy);
//        return camelContext;
//    }
//}
