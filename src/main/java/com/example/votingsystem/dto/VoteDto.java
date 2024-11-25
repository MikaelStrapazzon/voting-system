package com.example.votingsystem.dto;

import lombok.Data;

@Data
public class VoteDto {
  private Integer user;
  private Integer voteSession;
  private Boolean vote;
}
