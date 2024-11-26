package com.example.votingsystem.mapper.messaging;

import com.example.votingsystem.entity.User;
import com.example.votingsystem.messaging.producer.dto.UserCreateEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserProducerMapper {

  @Mapping(target = "id", source = "id")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "cpf", source = "cpf")
  @Mapping(target = "createdAt", source = "createdAt")
  UserCreateEvent userToUserCreateEvent(User user);
}
