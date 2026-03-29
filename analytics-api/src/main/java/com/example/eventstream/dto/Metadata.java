package com.example.eventstream.dto;

import lombok.Data;

@Data
public class Metadata {
  private String page;
  private String productId;
  private String productName;
  private double price;
  private String deviceType;
}
