package com.example.eventstream.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.eventstream.dto.EventRequest;
import com.example.eventstream.repository.interfaces.RedisRepositoryInterface;
import com.example.eventstream.service.interfaces.KafkaConsumerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

  private final ObjectMapper mapper;
  private final RedisRepositoryInterface redisRepository;

  public KafkaConsumerServiceImpl(RedisRepositoryInterface redisRepository) {
    this.mapper = new ObjectMapper();
    this.redisRepository = redisRepository;
  }

  @KafkaListener(topics="test-topic",groupId="test-consumer-group")
  @Override
  public void listener(EventRequest message){
    try {
      // String json = this.mapper.writeValueAsString(message);
      System.out.println(message);

      // Write to DB

      // Write to redis
      redisRepository.recordEvent(message.getEventType());
    } catch (Exception e) {
      // Errors should go to DLQ after 2 failed attempts
      e.printStackTrace();
    }
  }
}
