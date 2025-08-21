package com.techtez.config;

import jakarta.jms.Queue; // Important: Use jakarta.jms.Queue
import org.apache.activemq.artemis.jms.client.ActiveMQQueue; // For ActiveMQ Artemis
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JmsConfig {

    @Bean
    public Queue myQueue() {
        return new ActiveMQQueue("myQueue"); // Create the queue with the desired name
        // You can add more configuration here if needed for ActiveMQQueue
    }


   

}
