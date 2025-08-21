package com.techtez.message;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @JmsListener(destination = "test-queue", containerFactory = "jmsListenerContainerFactory")
    public void receiveMessage(Message message) throws JMSException {
        if (message instanceof TextMessage textMessage) {
            System.out.println("Received: " + textMessage.getText());
            
            // Acknowledge the message after processing
            textMessage.acknowledge();
            System.out.println("Message acknowledged.");
        }
    }
}
