package com.example.votingsystem.task;

import com.example.votingsystem.service.UserService;
import com.example.votingsystem.service.VoteSessionService;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CloseVoteSessionTasks {
  private static final int verifyTime = 30000;

  private final UserService userService;
  private final VoteSessionService voteSessionService;

  @Scheduled(fixedDelay = verifyTime)
  public void closeVoteSession() {
    LOGGER.info("Starting check for Vote Session that need to be closed : {}", LocalDateTime.now());

    var voteSessions = voteSessionService.findAllNeedClose();
    LOGGER.info("Found x that needs to be closed: {}", voteSessions.size());

    if (voteSessions.isEmpty()) {
      return;
    }

    Long totalUsers = userService.countUsers();

    ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    for (var voteSession : voteSessions) {
      LOGGER.info("Starting to close Vote Session: {}", voteSession.getId());
      executor.submit(() -> voteSessionService.close(voteSession.getId(), totalUsers));
      LOGGER.info("Closed Vote Session: {}", voteSession.getId());
    }

    executor.shutdown();
  }
}
