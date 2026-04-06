package com.example.eventstream.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.eventstream.model.EventRecord;
import com.example.eventstream.repository.interfaces.EventRepositoryInterface;
import com.example.eventstream.service.interfaces.EventService;

@Service
public class EventServiceImpl implements EventService {
  private final EventRepositoryInterface eventRepository;

  public EventServiceImpl(EventRepositoryInterface eventRepository) {
    this.eventRepository = eventRepository;
  }

  @Override
  public Page<EventRecord> getEventsByUserId(String userId, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return this.eventRepository.findByUserId(userId, pageable);
  }
}
