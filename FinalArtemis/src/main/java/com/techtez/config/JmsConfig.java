package com.techtez.config;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import javax.jms.ConnectionFactory;

@Configuration
public class JmsConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        // Configure the connection factory to connect to Artemis broker
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        
        // Optional: Use a connection factory wrapper for user credentials
        UserCredentialsConnectionFactoryAdapter factoryAdapter = new UserCredentialsConnectionFactoryAdapter();
        factoryAdapter.setTargetConnectionFactory(connectionFactory);
        factoryAdapter.setUsername("admin");  // Replace with your credentials
        factoryAdapter.setPassword("admin");  // Replace with your credentials
        
        return factoryAdapter;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setSessionTransacted(true); // Enable transactions
        factory.setSessionAcknowledgeMode(2); // 2 = CLIENT_ACKNOWLEDGE mode
        return factory;
    }
}
