package com.example.votingsystem.service;

import com.example.votingsystem.dto.UserCreateDto;
import com.example.votingsystem.entity.User;
import java.util.Optional;

public interface UserService {
  Boolean existsById(Integer id);

  Optional<User> findByCpf(String cpf);

  Long countUsers();

  User save(UserCreateDto user);
}
