package com.example.eventstream.service;

import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

import com.example.eventstream.dto.EventRequest;
import com.example.eventstream.model.EventRecord;
import com.example.eventstream.repository.interfaces.RedisRepositoryInterface;
import com.example.eventstream.repository.interfaces.EventRepositoryInterface;
import com.example.eventstream.service.interfaces.KafkaConsumerService;

@Service
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

  private final RedisRepositoryInterface redisRepository;
  private final EventRepositoryInterface eventRepository;

  public KafkaConsumerServiceImpl(RedisRepositoryInterface redisRepository, EventRepositoryInterface eventRepository) {
    this.redisRepository = redisRepository;
    this.eventRepository = eventRepository;
  }

  @RetryableTopic(attempts = "3", backoff = @Backoff(delay = 1000), dltTopicSuffix = ".dlt")
  @KafkaListener(topics="test-topic",groupId="test-consumer-group")
  @Override
  public void listener(EventRequest message){

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

    // Write to redis (Overall counts)
    redisRepository.recordEvent(eventRecord.getEventType());

    // Write to redis (Counts by userId)
    redisRepository.recordEvent(eventRecord.getUserId() + ":" + eventRecord.getEventType());
  }

  @DltHandler
  public void dltHandler(EventRequest message) {
    // Handle messages that failed after retries
    System.err.println("Message sent to DLT: " + message);
  }
}
