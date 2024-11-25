package com.example.votingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VoteDto {
  private Integer userId;
  private Integer voteSessionId;
  private Boolean vote;
}
