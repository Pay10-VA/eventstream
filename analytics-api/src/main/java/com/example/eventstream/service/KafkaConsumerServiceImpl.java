package com.example.eventstream.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.eventstream.dto.EventRequest;
import com.example.eventstream.service.interfaces.KafkaConsumerService;

@Service
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

  @KafkaListener(topics="test-topic",groupId="test-consumer-group")
  public void listener(EventRequest message) {
    System.out.println("hello");
  }
}
