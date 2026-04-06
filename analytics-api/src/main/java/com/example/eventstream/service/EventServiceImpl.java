package com.example.eventstream.service;

import org.springframework.stereotype.Service;

import com.example.eventstream.repository.interfaces.RedisRepositoryInterface;
import com.example.eventstream.service.interfaces.EventService;

@Service
public class EventServiceImpl implements EventService {
  private final RedisRepositoryInterface redisRepository;

  public EventServiceImpl(RedisRepositoryInterface redisRepository) {
    this.redisRepository = redisRepository;
  }

  @Override
  public void testing() {}
}
