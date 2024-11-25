package com.example.votingsystem.service;

import com.example.votingsystem.dto.UserCreateDto;
import com.example.votingsystem.entity.User;

public interface UserService {
  User save(UserCreateDto user);
}
