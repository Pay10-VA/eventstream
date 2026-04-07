package com.example.eventstream.model;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
public class EventCount {
  // When Mongo sees _id, it will automatically map it to the field annotated with @Field("_id")
  @Field("_id")
  private String EventType;
  private int count;
}
