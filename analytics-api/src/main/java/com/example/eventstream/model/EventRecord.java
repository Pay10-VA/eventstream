package com.example.eventstream.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


@Document(collection = "events")
@Data
public class EventRecord {

  @Id
  private String id;
  private String userId;
  private String sessionId;
  private String eventType;
  private String timestamp;
  private Metadata metadata;

  @Data
  public static class Metadata {
    private String page;
    private String productId;
    private String productName;
    private double price;
    private String deviceType;
  }

}
