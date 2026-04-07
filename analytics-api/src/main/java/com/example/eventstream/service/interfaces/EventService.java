package com.example.eventstream.service.interfaces;

import org.springframework.data.domain.Page;

import com.example.eventstream.model.EventRecord;

public interface EventService {
  public Page<EventRecord> getEventsByUserId(String userId, int page, int size);
  public Page<EventRecord> getEventsBySessionId(String sessionId, int page, int size);
}
