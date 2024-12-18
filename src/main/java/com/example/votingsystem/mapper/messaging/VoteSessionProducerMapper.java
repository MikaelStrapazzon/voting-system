package com.example.votingsystem.mapper.messaging;

import com.example.votingsystem.entity.SessionResults;
import com.example.votingsystem.entity.Vote;
import com.example.votingsystem.entity.VoteSession;
import infra.messaging.producer.dto.EndVoteSessionEvent;
import infra.messaging.producer.dto.OpenVoteSessionEvent;
import infra.messaging.producer.dto.UserVoteInVoteSessionEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VoteSessionProducerMapper {

  @Mapping(target = "id", source = "id")
  @Mapping(target = "title", source = "title")
  @Mapping(target = "description", source = "description")
  @Mapping(target = "startTime", source = "startTime")
  @Mapping(target = "endTime", source = "endTime")
  OpenVoteSessionEvent voteSessionToOpenVoteSessionEvent(VoteSession voteSession);

  @Mapping(target = "voteSessionId", source = "voteSessionId")
  @Mapping(target = "votesYes", source = "votesYes")
  @Mapping(target = "votesNo", source = "votesNo")
  @Mapping(target = "nonVoters", source = "nonVoters")
  @Mapping(target = "endTime", source = "createdAt")
  EndVoteSessionEvent sessionResultsToEndVoteSessionEvent(SessionResults sessionResults);

  @Mapping(target = "userId", source = "userId")
  @Mapping(target = "voteSessionId", source = "voteSessionId")
  @Mapping(target = "vote", source = "vote")
  UserVoteInVoteSessionEvent voteToUserVoteInVoteSessionEvent(Vote vote);
}
