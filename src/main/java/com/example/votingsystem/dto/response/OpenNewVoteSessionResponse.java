package com.example.votingsystem.dto.response;

import java.time.LocalDateTime;

public record OpenNewVoteSessionResponse(
    Integer id, String title, String description, LocalDateTime startTime, LocalDateTime endTime) {}
