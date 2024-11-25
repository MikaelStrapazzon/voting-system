package com.example.votingsystem.controller;

import static com.example.votingsystem.controller.util.Endpoints.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.example.votingsystem.dto.request.OpenNewVoteSessionRequest;
import com.example.votingsystem.dto.response.OpenNewVoteSessionResponse;
import com.example.votingsystem.dto.response.generic.SuccessResponse;
import com.example.votingsystem.mapper.controller.VoteSessionControllerMapper;
import com.example.votingsystem.service.VoteSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = V1_VOTE_SESSION, produces = APPLICATION_JSON_VALUE)
public class VoteSessionController {

  private final VoteSessionService voteSessionService;
  private final VoteSessionControllerMapper voteSessionControllerMapper;

  @PostMapping(value = V1_VOTE_SESSION_OPEN)
  public ResponseEntity<SuccessResponse<OpenNewVoteSessionResponse>> openNewVoteSession(
      @RequestBody OpenNewVoteSessionRequest request) {
    var voteSession =
        voteSessionService.openNew(
            voteSessionControllerMapper.openNewVoteSessionRequestToVoteSessionOpenDto(request));

    return ResponseEntity.ok(
        new SuccessResponse<>(
            voteSessionControllerMapper.voteSessionToOpenNewVoteSessionResponse(voteSession)));
  }
}
