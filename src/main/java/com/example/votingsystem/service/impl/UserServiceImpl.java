package com.example.votingsystem.service.impl;

import com.example.votingsystem.client.CpfValidatorClient;
import com.example.votingsystem.dto.UserCreateDto;
import com.example.votingsystem.entity.User;
import com.example.votingsystem.exception.custom.EntityValidationException;
import com.example.votingsystem.mapper.UserMapper;
import com.example.votingsystem.repository.UserRepository;
import com.example.votingsystem.service.UserService;
import infra.messaging.producer.UserProducer;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final CpfValidatorClient cpfValidatorClient;
  private final UserProducer userProducer;

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Override
  public Boolean existsById(Integer id) {
    return userRepository.existsById(id);
  }

  @Override
  public Optional<User> findByCpf(String cpf) {
    return userRepository.findByCpf(cpf);
  }

  @Override
  public Long countUsers() {
    return userRepository.count();
  }

  @Override
  @Transactional
  public User save(UserCreateDto newUser) {
    User user = userMapper.userCreateDtoToUser(newUser);

    validateUser(processData(user));

    user = userRepository.save(user);

    userProducer.createUserEvent(user);

    return user;
  }

  private User processData(User user) {
    if (user.getName() != null) {
      user.setName(user.getName().trim());
    }

    if (user.getCpf() != null) {
      user.setCpf(user.getCpf().replaceAll("\\D", ""));
    }

    return user;
  }

  private void validateUser(User user) {
    Map<String, String> errors = new HashMap<>();

    if (user.getName() == null || user.getName().isEmpty()) {
      errors.put("username", "Username is required");
    } else if (user.getName().length() > 50) {
      errors.put("username", "Username cannot exceed 50 characters");
    }

    cpfValidatorClient.validateCpf(user.getCpf());

    // TODO verify in external API
    if (user.getCpf() == null || user.getCpf().isEmpty()) {
      errors.put("cpf", "CPF is required");
    } else if (user.getCpf().length() != 11) {
      errors.put("cpf", "CPF must be exactly 11 digits");
    } else if (findByCpf(user.getCpf()).isPresent()) {
      errors.put("cpf", "CPF already registered");
    } else if (!validateCpf(user.getCpf())) {
      errors.put("cpf", "CPF is invalid");
    }

    if (!errors.isEmpty()) {
      throw new EntityValidationException(errors);
    }
  }

  private Boolean validateCpf(String cpf) {
    return cpfValidatorClient.validateCpf(cpf).valid();
  }
}
