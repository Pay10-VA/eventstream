package com.example.eventstream.repository.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.eventstream.dto.EventRequest;

@Repository
public interface EventRepositoryInterface extends MongoRepository<EventRequest, String> {
  public void saveEvent(EventRequest event);
}