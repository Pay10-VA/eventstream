package com.example.eventstream.repository.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.eventstream.model.EventCount;
import com.example.eventstream.model.EventRecord;

@Repository
public interface EventRepositoryInterface extends MongoRepository<EventRecord, String> {
  public Page<EventRecord> findByUserId(String userId, Pageable pageable);

  public Page<EventRecord> findBySessionId(String sessionId, Pageable pageable);

  @Aggregation(pipeline = {
    "{ $group: { _id: '$eventType', count: { $sum: 1 } } }",
    "{ $sort: { count: -1 } }",
    "{ $limit: 5 }"
  })
  public List<EventCount> getTop5MostFrequentEvents();
}