package com.example.votingsystem.dto;

import lombok.Data;

@Data
public class VoteDto {
  private Integer userId;
  private Integer voteSessionId;
  private Boolean vote;
}
