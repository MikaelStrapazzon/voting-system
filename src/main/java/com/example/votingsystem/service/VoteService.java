package com.example.votingsystem.service;

import com.example.votingsystem.dto.VoteDto;
import com.example.votingsystem.entity.Vote;

public interface VoteService {
  Vote voteInSession(VoteDto voteDto);
}
