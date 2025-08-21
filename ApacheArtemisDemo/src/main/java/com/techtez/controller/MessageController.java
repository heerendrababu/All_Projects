package com.techtez.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.techtez.Demo.MessageConsumer;
import com.techtez.Demo.MessageProducer;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api")
public class MessageController {

  private final MessageProducer producerService;
  private final MessageConsumer consumerService;

  @Autowired
  public MessageController(MessageProducer producerService, MessageConsumer consumerService) {
      this.producerService = producerService;
      this.consumerService = consumerService;
  }

  // Start consumers after bean initialization
  @PostConstruct
  public void startConsumers() {
      consumerService.startConsumers();
  }

  // Validation method
  private boolean isInvalid(String value) {
      return value == null || value.trim().isEmpty() || value.equalsIgnoreCase("null");
  }

  @PostMapping("/send")
  public ResponseEntity<String> sendMessage(
          @RequestParam String message,
          @RequestParam String filter,
          @RequestParam int priority,
          @RequestParam long expiration,
          @RequestParam boolean durable) {

      if (isInvalid(message) || isInvalid(filter)) {
          return ResponseEntity.badRequest().body("Content and filter should be valid.");
      }

      producerService.sendMessage(message, filter, priority, expiration, durable);
      return ResponseEntity.ok("Message successfully sent with filter [" + filter + "] and priority [" + priority + "].");
  }

  @GetMapping("/receive")
  public ResponseEntity<List<String>> receiveMessages() {
      List<String> messages = consumerService.getReceivedMessagesList();
      return ResponseEntity.ok(messages);
  }
}
