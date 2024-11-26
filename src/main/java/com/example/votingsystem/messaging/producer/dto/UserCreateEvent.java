package com.example.votingsystem.messaging.producer.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UserCreateEvent {
  private Integer id;
  private String name;
  private String cpf;
  private LocalDateTime createdAt;
}
