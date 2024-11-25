package com.example.votingsystem.mapper;

import com.example.votingsystem.dto.VoteDto;
import com.example.votingsystem.entity.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VoteMapper {
  @Mapping(target = "userId", source = "userId")
  @Mapping(target = "voteSessionId", source = "voteSessionId")
  @Mapping(target = "vote", source = "vote")
  Vote voteDtoToVote(VoteDto dto);
}
