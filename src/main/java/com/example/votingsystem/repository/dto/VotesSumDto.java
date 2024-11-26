package com.example.votingsystem.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VotesSumDto {
  private Long yesVotes;
  private Long noVotes;
}
