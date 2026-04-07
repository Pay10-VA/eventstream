package com.example.eventstream.model;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
public class ProductViewCount {
  @Field("_id")
  private String productId;
  private String productName;
  private int viewCount;
}
