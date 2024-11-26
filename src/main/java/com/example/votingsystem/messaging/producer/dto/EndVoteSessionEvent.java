package com.example.votingsystem.messaging.producer.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class EndVoteSessionEvent {
  String voteSessionId;
  Long votesYes;
  Long votesNo;
  Long nonVoters;
  LocalDateTime endTime;
}
