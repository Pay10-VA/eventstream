package com.example.eventstream.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EventRequest {

  @NotBlank
  private String userId;

  @NotBlank
  private String sessionId;

  @NotBlank
  private String eventType;

  @NotBlank
  private String timestamp;
  
  private Metadata metadata;
}
