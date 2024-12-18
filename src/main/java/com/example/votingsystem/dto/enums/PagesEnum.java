package com.example.votingsystem.dto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PagesEnum {
  NewVoteSession("NewVoteSession.json"),
  UserVote("UserVote.json"),
  VoteSessionResult("VoteSessionResult.json");

  private final String jsonFileName;
}
