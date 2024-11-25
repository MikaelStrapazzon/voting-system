package com.example.votingsystem.service;

import com.example.votingsystem.dto.VoteDto;
import com.example.votingsystem.entity.Vote;

public interface VoteService {
  Boolean existVote(Integer voteSessionId, Integer userId);

  Vote voteInSession(VoteDto voteDto);
}
