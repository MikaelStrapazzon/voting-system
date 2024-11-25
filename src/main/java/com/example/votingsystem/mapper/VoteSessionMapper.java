package com.example.votingsystem.mapper;

import com.example.votingsystem.dto.VoteSessionOpenDto;
import com.example.votingsystem.entity.VoteSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VoteSessionMapper {
  @Mapping(target = "title", source = "title")
  @Mapping(target = "description", source = "description")
  VoteSession voteSessionOpenDtoToVoteSession(VoteSessionOpenDto dto);
}
