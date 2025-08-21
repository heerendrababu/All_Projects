package com.techtez.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam; // Use @RequestParam
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageProducerController {

    @Autowired
    private JmsTemplate jmsTemplate;

    @PostMapping("/queue/sendMessage") // URL: http://localhost:8080/queue/sendMessage
    public String sendMessage(@RequestParam("message") String message) { // Get message from query parameter
        jmsTemplate.convertAndSend("myQueue", message);
        return "Message sent successfully!";
    }
}