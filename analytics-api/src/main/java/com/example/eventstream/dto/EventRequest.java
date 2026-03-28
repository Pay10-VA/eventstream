package com.example.eventstream.dto;

import lombok.Data;

@Data
public class EventRequest {
  private String userId;
  private String sessionId;
  private String eventType;
  private String timestamp;
  private Metadata metadata;
}
