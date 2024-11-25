package com.example.votingsystem.service;

import com.example.votingsystem.dto.VoteDto;
import com.example.votingsystem.dto.VoteSessionOpenDto;
import com.example.votingsystem.dto.VoteSessionUpdateDto;
import com.example.votingsystem.entity.SessionResults;
import com.example.votingsystem.entity.Vote;
import com.example.votingsystem.entity.VoteSession;
import java.util.List;
import java.util.Optional;

public interface VoteSessionService {
  Optional<VoteSession> findById(Integer id);

  List<VoteSession> findAllNeedClose();

  Boolean existVote(Integer voteSessionId, Integer userId);

  Vote voteInSession(VoteDto voteDto);

  VoteSession openNew(VoteSessionOpenDto voteSessionStartDto);

  VoteSession update(VoteSession old, VoteSessionUpdateDto updateDto);

  SessionResults close(Integer voteSessionId);

  SessionResults close(Integer voteSessionId, Long allUsers);
}
