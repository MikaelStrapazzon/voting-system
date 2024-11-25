package com.example.votingsystem.service.impl;

import com.example.votingsystem.dto.VoteSessionOpenDto;
import com.example.votingsystem.entity.VoteSession;
import com.example.votingsystem.exception.custom.EntityValidationException;
import com.example.votingsystem.mapper.VoteSessionMapper;
import com.example.votingsystem.repository.VoteSessionRepository;
import com.example.votingsystem.service.VoteSessionService;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VoteSessionServiceImpl implements VoteSessionService {

  private final VoteSessionRepository voteSessionRepository;
  private final VoteSessionMapper voteSessionMapper;

  @Override
  public Optional<VoteSession> findById(Integer id) {
    return voteSessionRepository.findById(id);
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

    validateVoteSession(processData(voteSession, voteSessionStartDto.getDuration()));

    return voteSessionRepository.save(voteSession);
  }

  private VoteSession processData(VoteSession voteSession, Integer duration) {
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
