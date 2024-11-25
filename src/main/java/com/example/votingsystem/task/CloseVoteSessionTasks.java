package com.example.votingsystem.task;

import com.example.votingsystem.service.UserService;
import com.example.votingsystem.service.VoteSessionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CloseVoteSessionTasks {
  private static final int verifyTime = 30000;

  private final UserService userService;
  private final VoteSessionService voteSessionService;

  @Scheduled(fixedDelay = verifyTime)
  public void closeVoteSession() {
    var voteSessions = voteSessionService.findAllNeedClose();

    if (voteSessions.isEmpty()) {
      return;
    }

    Long totalUsers = userService.countUsers();

    ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    for (var voteSession : voteSessions) {
      executor.submit(() -> voteSessionService.close(voteSession.getId(), totalUsers));
    }

    executor.shutdown();
  }
}
