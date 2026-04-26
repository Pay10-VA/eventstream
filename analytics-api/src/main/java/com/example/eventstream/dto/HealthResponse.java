package com.example.eventstream.dto;

public class HealthResponse {
    private String status;
    private String timestamp;

    public HealthResponse(String status, String timestamp) {
      this.status = status;
      this.timestamp = timestamp;
    }

    public String getStatus() { return status; }
    public String getTimestamp() { return timestamp; }
    public void setStatus(String status) { this.status = status; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }  
  }