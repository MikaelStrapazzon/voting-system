package com.example.votingsystem.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class VoteSessionResultDto {
  String title;
  String description;
  LocalDateTime start;
  LocalDateTime end;
  Boolean open;
  Long yesVotes;
  Long noVotes;
  Long nonVotes;
}
