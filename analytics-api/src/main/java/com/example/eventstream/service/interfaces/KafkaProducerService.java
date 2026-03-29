package com.example.eventstream.service.interfaces;

import com.example.eventstream.dto.EventRequest;

public interface KafkaProducerService {
  public void publishEvent(EventRequest eventRequest);
}
