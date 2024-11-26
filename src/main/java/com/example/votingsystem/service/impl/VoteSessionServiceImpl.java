package com.example.votingsystem.service.impl;

import com.example.votingsystem.dto.*;
import com.example.votingsystem.entity.SessionResults;
import com.example.votingsystem.entity.Vote;
import com.example.votingsystem.entity.VoteSession;
import com.example.votingsystem.exception.custom.EntityValidationException;
import com.example.votingsystem.exception.custom.NotFoundException;
import com.example.votingsystem.mapper.SessionResultsMapper;
import com.example.votingsystem.mapper.VoteSessionMapper;
import com.example.votingsystem.mapper.VoteSessionResultDtoMapper;
import com.example.votingsystem.messaging.producer.VoteSessionProducer;
import com.example.votingsystem.repository.SessionResultsRepository;
import com.example.votingsystem.repository.VoteRepository;
import com.example.votingsystem.repository.VoteSessionRepository;
import com.example.votingsystem.repository.dto.VotesSumDto;
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

  private final VoteSessionProducer voteSessionProducer;

  private final VoteSessionRepository voteSessionRepository;
  private final VoteRepository voteRepository;
  private final SessionResultsRepository sessionResultsRepository;

  private final VoteSessionMapper voteSessionMapper;
  private final SessionResultsMapper sessionResultsMapper;
  private final VoteSessionResultDtoMapper voteSessionResultDtoMapper;

  @Override
  public Optional<VoteSession> findById(Integer id) {
    return voteSessionRepository.findById(id);
  }

  @Override
  public Optional<SessionResults> findSessionResultByVoteSessionId(Integer id) {
    return sessionResultsRepository.findById(id);
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

    voteSessionProducer.openVoteSession(voteSession);

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

    SessionResults results =
        saveSessionResults(generateVoteSessionResults(voteSessionId, allUsers));

    voteSessionProducer.endVoteSession(results);

    return results;
  }

  @Override
  public VoteSessionResultDto voteSessionResult(Integer voteSessionId) {
    var voteSession =
        findById(voteSessionId).orElseThrow(() -> new NotFoundException("Vote session not found"));

    VoteSessionResultDto resultDto;

    if (!voteSession.getOpen()) {
      resultDto =
          voteSessionResultDtoMapper.VoteSessionAndSessionResultsToVoteSessionResultDto(
              voteSession,
              findSessionResultByVoteSessionId(voteSessionId)
                  .orElseThrow(
                      () ->
                          new NotFoundException(
                              "Voting session closed but no results were counted")));
      resultDto.setOpen(false);
    } else {
      resultDto =
          voteSessionResultDtoMapper.VoteSessionAndSessionResultsDtoToVoteSessionResultDto(
              voteSession, generateVoteSessionResults(voteSessionId));
      resultDto.setOpen(true);
    }

    return resultDto;
  }

  @Override
  @Transactional
  public Vote voteInSession(VoteDto voteDto) {
    Vote vote = voteSessionMapper.voteDtoToVote(voteDto);

    validateVote(processVote(vote));

    vote = voteRepository.save(vote);
    voteSessionProducer.userVotedInVoteSession(vote);

    return vote;
  }

  @Transactional
  protected SessionResults saveSessionResults(SessionResultsDto sessionResultsDto) {
    SessionResults sessionResults =
        sessionResultsMapper.sessionResultsDtoToSessionResults(sessionResultsDto);

    validateSessionResults(sessionResults);

    return sessionResultsRepository.save(sessionResults);
  }

  private SessionResultsDto generateVoteSessionResults(Integer voteSessionId) {
    return generateVoteSessionResults(voteSessionId, userService.countUsers());
  }

  private SessionResultsDto generateVoteSessionResults(Integer voteSessionId, Long countUsers) {
    var validVotes = calculateValidVotes(voteSessionId);

    return new SessionResultsDto(
        voteSessionId,
        validVotes.yesVotes(),
        validVotes.noVotes(),
        countUsers - validVotes.yesVotes() - validVotes.noVotes());
  }

  private ValidVotes calculateValidVotes(Integer voteSessionId) {
    VotesSumDto votesSumDto = voteRepository.countVotes(voteSessionId);

    return new ValidVotes(votesSumDto.getYesVotes(), votesSumDto.getNoVotes());
  }

  private Vote processVote(Vote vote) {
    return vote;
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
