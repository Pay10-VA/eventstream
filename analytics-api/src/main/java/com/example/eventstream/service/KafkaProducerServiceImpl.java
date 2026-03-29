package com.example.eventstream.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.eventstream.dto.EventRequest;
import com.example.eventstream.service.interfaces.KafkaProducerService;

@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {
  private final KafkaTemplate<String, EventRequest> kafkaTemplate;
  
  public KafkaProducerServiceImpl(KafkaTemplate<String, EventRequest> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void publishEvent(EventRequest message) {
    kafkaTemplate.send("test-topic", message);
  }
}
