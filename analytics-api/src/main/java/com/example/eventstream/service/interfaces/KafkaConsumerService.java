package com.example.eventstream.service.interfaces;

import com.example.eventstream.dto.EventRequest;

public interface KafkaConsumerService {
  public void listener(EventRequest eventRequest);
}
