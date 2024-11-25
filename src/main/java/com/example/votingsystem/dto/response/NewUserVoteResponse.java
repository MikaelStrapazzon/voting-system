package com.example.votingsystem.dto.response;

import java.time.LocalDateTime;

public record NewUserVoteResponse(
    Integer id, Integer userId, Integer voteSessionId, Boolean vote, LocalDateTime votedAt) {}
