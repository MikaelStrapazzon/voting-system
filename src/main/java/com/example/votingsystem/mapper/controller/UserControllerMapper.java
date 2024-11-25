package com.example.votingsystem.mapper.controller;

import com.example.votingsystem.dto.UserCreateDto;
import com.example.votingsystem.dto.request.UserCreateRequest;
import com.example.votingsystem.dto.response.CreateUserResponse;
import com.example.votingsystem.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserControllerMapper {
  @Mapping(target = "name", source = "name")
  @Mapping(target = "cpf", source = "cpf")
  UserCreateDto userCreateRequestToUserCreateDto(UserCreateRequest request);

  @Mapping(target = "id", source = "id")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "cpf", source = "cpf")
  @Mapping(target = "createdAt", source = "createdAt")
  CreateUserResponse UserToCreateUserResponse(User user);
}
