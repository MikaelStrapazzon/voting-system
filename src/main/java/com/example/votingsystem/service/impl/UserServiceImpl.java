package com.example.votingsystem.service.impl;

import com.example.votingsystem.dto.UserCreateDto;
import com.example.votingsystem.entity.User;
import com.example.votingsystem.exception.custom.ValidationException;
import com.example.votingsystem.mapper.UserMapper;
import com.example.votingsystem.repository.UserRepository;
import com.example.votingsystem.service.UserService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Override
  @Transactional
  public User save(UserCreateDto newUser) {
    User user = userMapper.userCreateDtoToUser(newUser);

    validateUser(processData(user));

    return userRepository.save(user);
  }

  private User processData(User user) {
    if (user.getUsername() != null) {
      user.setUsername(user.getUsername().trim());
    }

    if (user.getCpf() != null) {
      user.setCpf(user.getCpf().replaceAll("\\D", ""));
    }

    return user;
  }

  private void validateUser(User user) {
    Map<String, String> errors = new HashMap<>();

    if (user.getUsername() == null || user.getUsername().isEmpty()) {
      errors.put("username", "Username is required");
    } else if (user.getUsername().length() > 50) {
      errors.put("username", "Username cannot exceed 50 characters");
    }

    // TODO verify in external API
    if (user.getCpf() == null || user.getCpf().isEmpty()) {
      errors.put("cpf", "CPF is required");
    } else if (user.getCpf().length() != 11) {
      errors.put("cpf", "CPF must be exactly 11 digits");
    }

    if (!errors.isEmpty()) {
      throw new ValidationException(errors);
    }
  }
}
