package com.example.votingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SessionResultsDto {
  private Integer voteSessionId;
  private Long votesYes;
  private Long votesNo;
  private Long nonVoters;
}
