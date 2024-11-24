package com.example.votingsystem.service.impl;

import com.example.votingsystem.dto.VoteDto;
import com.example.votingsystem.dto.VoteSessionOpenDto;
import com.example.votingsystem.dto.VoteSessionUpdateDto;
import com.example.votingsystem.dto.repository.VotesSumDto;
import com.example.votingsystem.entity.SessionResults;
import com.example.votingsystem.entity.Vote;
import com.example.votingsystem.entity.VoteSession;
import com.example.votingsystem.exception.custom.EntityValidationException;
import com.example.votingsystem.exception.custom.NotFoundException;
import com.example.votingsystem.mapper.VoteSessionMapper;
import com.example.votingsystem.repository.SessionResultsRepository;
import com.example.votingsystem.repository.VoteRepository;
import com.example.votingsystem.repository.VoteSessionRepository;
import com.example.votingsystem.service.UserService;
import com.example.votingsystem.service.VoteSessionService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VoteSessionServiceImpl implements VoteSessionService {

  private final UserService userService;

  private final VoteSessionRepository voteSessionRepository;
  private final VoteRepository voteRepository;
  private final SessionResultsRepository sessionResultsRepository;

  private final VoteSessionMapper voteSessionMapper;

  @Override
  public Optional<VoteSession> findById(Integer id) {
    return voteSessionRepository.findById(id);
  }

  @Override
  public List<VoteSession> findAllNeedClose() {
    return voteSessionRepository.findNeedClose();
  }

  @Override
  public Boolean existVote(Integer voteSessionId, Integer userId) {
    return voteRepository.existsByVoteSessionIdAndUserId(voteSessionId, userId);
  }

  @Override
  @Transactional
  public VoteSession openNew(VoteSessionOpenDto voteSessionStartDto) {
    voteSessionStartDto.setDuration(
        voteSessionStartDto.getDuration() == null ? 60 : voteSessionStartDto.getDuration());

    if (voteSessionStartDto.getDuration() < 1 || voteSessionStartDto.getDuration() > 525960) {
      throw new EntityValidationException(
          "duration", "Duration must be between 1 and 525959 minutes (1 year)");
    }

    VoteSession voteSession =
        voteSessionMapper.voteSessionOpenDtoToVoteSession(voteSessionStartDto);

    validateVoteSession(processVoteSession(voteSession, voteSessionStartDto.getDuration()));

    return voteSessionRepository.save(voteSession);
  }

  @Override
  @Transactional
  public VoteSession update(VoteSession old, VoteSessionUpdateDto updateDto) {
    old.setOpen(updateDto.isOpen());

    validateVoteSession(old);

    return voteSessionRepository.save(old);
  }

  @Override
  public SessionResults close(Integer voteSessionId) {
    return close(voteSessionId, userService.countUsers());
  }

  @Override
  @Transactional
  public SessionResults close(Integer voteSessionId, Long allUsers) {
    var voteSession =
        findById(voteSessionId)
            .orElseThrow(
                () ->
                    new NotFoundException("Vote session with id " + voteSessionId + " not found"));

    update(voteSession, new VoteSessionUpdateDto(false));

    return calculateSessionResults(voteSessionId, allUsers);
  }

  @Override
  @Transactional
  public Vote voteInSession(VoteDto voteDto) {
    Vote vote = voteSessionMapper.voteDtoToVote(voteDto);

    validateVote(processVote(vote));

    return voteRepository.save(vote);
  }

  @Transactional
  protected SessionResults calculateSessionResults(Integer voteSessionId, Long userCount) {
    SessionResults sessionResults = new SessionResults();
    sessionResults.setVoteSessionId(voteSessionId);

    VotesSumDto votesSumDto = voteRepository.countVotes(voteSessionId);
    sessionResults.setVotesYes(votesSumDto.getYesVotes());
    sessionResults.setVotesNo(votesSumDto.getNoVotes());
    sessionResults.setNonVoters(
        userCount
            - (votesSumDto.getYesVotes() != null ? votesSumDto.getYesVotes() : 0)
            - (votesSumDto.getNoVotes() != null ? votesSumDto.getNoVotes() : 0));

    validateSessionResults(sessionResults);

    return sessionResultsRepository.save(sessionResults);
  }

  private Vote processVote(Vote vote) {
    return vote;
  }

  private void validateSessionResults(SessionResults sessionResults) {
    // TODO
    return;
  }

  private void validateVote(Vote vote) {
    Map<String, String> errors = new HashMap<>();

    if (vote.getUserId() == null || !userService.existsById(vote.getUserId())) {
      errors.put("userId", "User not informed or does not exist");
    }

    if (vote.getVoteSessionId() != null) {
      var voteSession = findById(vote.getVoteSessionId());

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

  private VoteSession processVoteSession(VoteSession voteSession, Integer duration) {
    if (voteSession.getTitle() != null) {
      voteSession.setTitle(voteSession.getTitle().trim());
    }

    if (voteSession.getDescription() != null) {
      voteSession.setDescription(voteSession.getDescription().trim());
    }

    voteSession.setStartTime(LocalDateTime.now());
    voteSession.setEndTime(voteSession.getStartTime().plusMinutes(duration));
    voteSession.setOpen(true);

    return voteSession;
  }

  private void validateVoteSession(VoteSession voteSession) {
    Map<String, String> errors = new HashMap<>();

    if (voteSession.getTitle() == null || voteSession.getTitle().isEmpty()) {
      errors.put("title", "Title is required");
    } else if (voteSession.getTitle().length() > 255) {
      errors.put("title", "Title cannot exceed 255 characters");
    }

    if (voteSession.getStartTime() == null || voteSession.getEndTime() == null) {
      errors.put("opening_window", "Start and end date are required");
    } else if (!voteSession.getEndTime().isAfter(voteSession.getStartTime())
        || ChronoUnit.YEARS.between(voteSession.getStartTime(), voteSession.getEndTime()) >= 1) {
      errors.put("opening_window", "Session end must be between 1 minute and 1 year from start");
    }

    if (voteSession.getOpen() == null) {
      errors.put("open", "A voting session must have its status as either open or closed ");
    }

    if (!errors.isEmpty()) {
      throw new EntityValidationException(errors);
    }
  }
}
