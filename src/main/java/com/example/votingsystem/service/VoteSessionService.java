package com.example.votingsystem.service;

import com.example.votingsystem.dto.VoteSessionOpenDto;
import com.example.votingsystem.entity.VoteSession;

public interface VoteSessionService {
  VoteSession openNew(VoteSessionOpenDto voteSessionStartDto);
}
