package com.example.votingsystem.service;

import com.example.votingsystem.dto.UserCreateDto;
import com.example.votingsystem.entity.User;
import java.util.Optional;

public interface UserService {
  Optional<User> findByCpf(String cpf);

  User save(UserCreateDto user);
}
