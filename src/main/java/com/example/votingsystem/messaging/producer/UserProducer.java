package com.example.votingsystem.messaging.producer;

import com.example.votingsystem.entity.User;
import com.example.votingsystem.mapper.messaging.UserProducerMapper;
import com.example.votingsystem.messaging.producer.dto.UserCreateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProducer {
  private final StreamBridge streamBridge;
  private final UserProducerMapper userProducerMapper;

  public void createUserEvent(User user) {
    createUserEvent(userProducerMapper.userToUserCreateEvent(user));
  }

  public void createUserEvent(UserCreateEvent userCreateEvent) {
    streamBridge.send("producer-create-user", userCreateEvent);
  }
}
