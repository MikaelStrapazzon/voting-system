package com.example.votingsystem.mapper;

import com.example.votingsystem.dto.SessionResultsDto;
import com.example.votingsystem.dto.VoteSessionResultDto;
import com.example.votingsystem.entity.SessionResults;
import com.example.votingsystem.entity.VoteSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VoteSessionResultDtoMapper {
  @Mapping(target = "title", source = "voteSession.title")
  @Mapping(target = "description", source = "voteSession.description")
  @Mapping(target = "start", source = "voteSession.startTime")
  @Mapping(target = "end", source = "voteSession.endTime")
  @Mapping(target = "yesVotes", source = "sessionResults.votesYes")
  @Mapping(target = "noVotes", source = "sessionResults.votesNo")
  @Mapping(target = "nonVotes", source = "sessionResults.nonVoters")
  VoteSessionResultDto VoteSessionAndSessionResultsDtoToVoteSessionResultDto(
      VoteSession voteSession, SessionResultsDto sessionResults);

  @Mapping(target = "title", source = "voteSession.title")
  @Mapping(target = "description", source = "voteSession.description")
  @Mapping(target = "start", source = "voteSession.startTime")
  @Mapping(target = "end", source = "voteSession.endTime")
  @Mapping(target = "yesVotes", source = "sessionResults.votesYes")
  @Mapping(target = "noVotes", source = "sessionResults.votesNo")
  @Mapping(target = "nonVotes", source = "sessionResults.nonVoters")
  VoteSessionResultDto VoteSessionAndSessionResultsToVoteSessionResultDto(
      VoteSession voteSession, SessionResults sessionResults);
}
