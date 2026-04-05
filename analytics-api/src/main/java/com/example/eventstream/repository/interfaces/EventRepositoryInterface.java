package com.example.eventstream.repository.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.eventstream.model.EventRecord;

@Repository
public interface EventRepositoryInterface extends MongoRepository<EventRecord, String> {
}