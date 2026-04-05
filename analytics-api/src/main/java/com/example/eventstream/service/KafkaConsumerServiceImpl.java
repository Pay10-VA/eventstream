package com.example.eventstream.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.eventstream.dto.EventRequest;
import com.example.eventstream.model.EventRecord;
import com.example.eventstream.repository.interfaces.RedisRepositoryInterface;
import com.example.eventstream.repository.interfaces.EventRepositoryInterface;
import com.example.eventstream.service.interfaces.KafkaConsumerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

  private final ObjectMapper mapper;
  private final RedisRepositoryInterface redisRepository;
  private final EventRepositoryInterface eventRepository;

  public KafkaConsumerServiceImpl(RedisRepositoryInterface redisRepository, EventRepositoryInterface eventRepository) {
    this.mapper = new ObjectMapper();
    this.redisRepository = redisRepository;
    this.eventRepository = eventRepository;
  }

  @KafkaListener(topics="test-topic",groupId="test-consumer-group")
  @Override
  public void listener(EventRequest message){
    try {
      // String json = this.mapper.writeValueAsString(message);
      System.out.println(message);

      // Convert Metadata
      EventRecord.Metadata metadata = new EventRecord.Metadata();
      metadata.setPage(message.getMetadata().getPage());
      metadata.setProductId(message.getMetadata().getProductId());
      metadata.setProductName(message.getMetadata().getProductName());
      metadata.setPrice(message.getMetadata().getPrice());
      metadata.setDeviceType(message.getMetadata().getDeviceType());

      // Convert Message to EventRecord
      EventRecord eventRecord = new EventRecord();
      eventRecord.setUserId(message.getUserId());
      eventRecord.setSessionId(message.getSessionId());
      eventRecord.setEventType(message.getEventType());
      eventRecord.setTimestamp(message.getTimestamp());
      eventRecord.setMetadata(metadata);

      // Write to DB
      eventRepository.save(eventRecord);

      // Write to redis
      redisRepository.recordEvent(eventRecord.getEventType());
    } catch (Exception e) {
      // Errors should go to DLQ after 2 failed attempts
      e.printStackTrace();
    }
  }
}
