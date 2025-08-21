package com.techtez.message;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MessageProducer {

    @Autowired
    private JmsTemplate jmsTemplate;

    private static final String QUEUE_NAME = "test-queue";

    @PostMapping("/send")
    public String sendMessage(@RequestParam String message) {
        jmsTemplate.convertAndSend(QUEUE_NAME, message);
        return "Message sent: " + message;
    }
}
