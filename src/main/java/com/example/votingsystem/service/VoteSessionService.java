package com.example.votingsystem.service;

import com.example.votingsystem.dto.VoteDto;
import com.example.votingsystem.dto.VoteSessionOpenDto;
import com.example.votingsystem.entity.Vote;
import com.example.votingsystem.entity.VoteSession;
import java.util.Optional;

public interface VoteSessionService {
  Optional<VoteSession> findById(Integer id);

  Boolean existVote(Integer voteSessionId, Integer userId);

  Vote voteInSession(VoteDto voteDto);

  VoteSession openNew(VoteSessionOpenDto voteSessionStartDto);
}
