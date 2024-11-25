package com.example.votingsystem.mapper;

import com.example.votingsystem.dto.UserCreateDto;
import com.example.votingsystem.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
  @Mapping(target = "name", source = "name")
  @Mapping(target = "cpf", source = "cpf")
  User userCreateDtoToUser(UserCreateDto userCreateDto);
}
