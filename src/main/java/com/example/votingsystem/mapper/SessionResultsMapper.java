package com.example.votingsystem.mapper;

import com.example.votingsystem.dto.SessionResultsDto;
import com.example.votingsystem.entity.SessionResults;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SessionResultsMapper {
  @Mapping(target = "voteSessionId", source = "voteSessionId")
  @Mapping(target = "votesYes", source = "votesYes")
  @Mapping(target = "votesNo", source = "votesNo")
  @Mapping(target = "nonVoters", source = "nonVoters")
  SessionResults sessionResultsDtoToSessionResults(SessionResultsDto dto);
}
