package com.example.votingsystem.mapper.controller;

import com.example.votingsystem.dto.VoteSessionOpenDto;
import com.example.votingsystem.dto.request.OpenNewVoteSessionRequest;
import com.example.votingsystem.dto.response.NewUserVoteResponse;
import com.example.votingsystem.dto.response.OpenNewVoteSessionResponse;
import com.example.votingsystem.entity.Vote;
import com.example.votingsystem.entity.VoteSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VoteSessionControllerMapper {
  @Mapping(target = "title", source = "title")
  @Mapping(target = "description", source = "description")
  VoteSessionOpenDto openNewVoteSessionRequestToVoteSessionOpenDto(
      OpenNewVoteSessionRequest request);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "title", source = "title")
  @Mapping(target = "description", source = "description")
  @Mapping(target = "startTime", source = "startTime")
  @Mapping(target = "endTime", source = "endTime")
  OpenNewVoteSessionResponse voteSessionToOpenNewVoteSessionResponse(VoteSession voteSession);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "userId", source = "userId")
  @Mapping(target = "voteSessionId", source = "voteSessionId")
  @Mapping(target = "vote", source = "vote")
  @Mapping(target = "votedAt", source = "votedAt")
  NewUserVoteResponse voteToNewUserVoteResponse(Vote vote);
}
