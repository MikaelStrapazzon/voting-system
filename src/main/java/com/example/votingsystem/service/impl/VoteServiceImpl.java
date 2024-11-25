package com.example.votingsystem.service.impl;

import com.example.votingsystem.dto.VoteDto;
import com.example.votingsystem.entity.Vote;
import com.example.votingsystem.exception.custom.EntityValidationException;
import com.example.votingsystem.mapper.VoteMapper;
import com.example.votingsystem.repository.VoteRepository;
import com.example.votingsystem.service.UserService;
import com.example.votingsystem.service.VoteService;
import com.example.votingsystem.service.VoteSessionService;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

  private final UserService userService;
  private final VoteSessionService voteSessionService;

  private final VoteRepository voteRepository;
  private final VoteMapper voteMapper;

  @Override
  public Boolean existVote(Integer voteSessionId, Integer userId) {
    return voteRepository.existsByVoteSessionIdAndUserId(voteSessionId, userId);
  }

  @Override
  @Transactional
  public Vote voteInSession(VoteDto voteDto) {
    Vote vote = voteMapper.voteDtoToVote(voteDto);

    validateVote(processVote(vote));

    return voteRepository.save(vote);
  }

  private Vote processVote(Vote vote) {
    return vote;
  }

  private void validateVote(Vote vote) {
    Map<String, String> errors = new HashMap<>();

    if (vote.getUserId() == null || userService.existsById(vote.getUserId())) {
      errors.put("userId", "User not informed or does not exist");
    }

    if (vote.getVoteSessionId() != null) {
      var voteSession = voteSessionService.findById(vote.getVoteSessionId());

      if (voteSession.isEmpty()) {
        errors.put("voteSessionId", "Vote session does not exist");
      } else if (!voteSession.get().getOpen()) {
        errors.put(
            "voteSessionId",
            "Vote session is closed in "
                + voteSession
                    .get()
                    .getEndTime()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
      }
    } else {
      errors.put("voteSessionId", "Vote session is required");
    }

    if (vote.getVote() == null) {
      errors.put("vote", "Vote is required");
    }

    if (errors.isEmpty() && existVote(vote.getVoteSessionId(), vote.getUserId())) {
      errors.put(
          "vote",
          "User " + vote.getUserId() + " has already voted in session " + vote.getVoteSessionId());
    }

    if (!errors.isEmpty()) {
      throw new EntityValidationException(errors);
    }
  }
}
