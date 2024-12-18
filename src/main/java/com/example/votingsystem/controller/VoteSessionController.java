package com.example.votingsystem.controller;

import static com.example.votingsystem.controller.util.Endpoints.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.example.votingsystem.dto.VoteDto;
import com.example.votingsystem.dto.request.NewUserVoteRequest;
import com.example.votingsystem.dto.request.OpenNewVoteSessionRequest;
import com.example.votingsystem.dto.response.NewUserVoteResponse;
import com.example.votingsystem.dto.response.OpenNewVoteSessionResponse;
import com.example.votingsystem.dto.response.VoteSessionResultResponse;
import com.example.votingsystem.dto.response.generic.SuccessResponse;
import com.example.votingsystem.mapper.controller.VoteSessionControllerMapper;
import com.example.votingsystem.service.VoteSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = V1_VOTE_SESSION, produces = APPLICATION_JSON_VALUE)
public class VoteSessionController {

  private final VoteSessionService voteSessionService;
  private final VoteSessionControllerMapper voteSessionControllerMapper;

  @PostMapping(value = V1_VOTE_SESSION_OPEN)
  public ResponseEntity<SuccessResponse<OpenNewVoteSessionResponse>> openNewVoteSession(
      @RequestBody OpenNewVoteSessionRequest request) {
    LOGGER.info("Opening Vote Session: {}", request.title());

    var voteSession =
        voteSessionService.openNew(
            voteSessionControllerMapper.openNewVoteSessionRequestToVoteSessionOpenDto(request));

    LOGGER.info("Open Vote Session: {} - {}", voteSession.getId(), voteSession.getTitle());

    return ResponseEntity.ok(
        new SuccessResponse<>(
            voteSessionControllerMapper.voteSessionToOpenNewVoteSessionResponse(voteSession)));
  }

  @PostMapping(value = V1_VOTE_SESSION_NEW_USER_VOTE)
  public ResponseEntity<SuccessResponse<NewUserVoteResponse>> newUserVote(
      @PathVariable Integer sessionId,
      @PathVariable Integer userId,
      @RequestBody NewUserVoteRequest request) {
    LOGGER.info("Including user vote in Vote Session: {} - {}", userId, sessionId);

    var vote = voteSessionService.voteInSession(new VoteDto(userId, sessionId, request.vote()));

    LOGGER.info("Included user vote in Vote Session: {} - {}", userId, sessionId);

    return ResponseEntity.ok(
        new SuccessResponse<>(voteSessionControllerMapper.voteToNewUserVoteResponse(vote)));
  }

  @GetMapping(value = V1_VOTE_SESSION_GET_RESULT)
  public ResponseEntity<SuccessResponse<VoteSessionResultResponse>> getResult(
      @PathVariable Integer sessionId) {
    var result = voteSessionService.voteSessionResult(sessionId);

    return ResponseEntity.ok(
        new SuccessResponse<>(
            voteSessionControllerMapper.voteSessionResultDtoToVoteSessionResultResponse(result)));
  }
}
