package com.example.eventstream.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.eventstream.dto.EventRequest;
import com.example.eventstream.service.interfaces.KafkaProducerService;

import java.util.UUID;

@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {
  private final KafkaTemplate<String, EventRequest> kafkaTemplate;
  
  public KafkaProducerServiceImpl(KafkaTemplate<String, EventRequest> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void publishEvent(EventRequest message) {
    message.setEventId(UUID.randomUUID().toString());
    kafkaTemplate.send("test-topic", message);
  }
}
