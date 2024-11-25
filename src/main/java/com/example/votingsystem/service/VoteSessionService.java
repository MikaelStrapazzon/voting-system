package com.example.votingsystem.service;

import com.example.votingsystem.dto.VoteSessionOpenDto;
import com.example.votingsystem.entity.VoteSession;
import java.util.Optional;

public interface VoteSessionService {
  Optional<VoteSession> findById(Integer id);

  VoteSession openNew(VoteSessionOpenDto voteSessionStartDto);
}
