package com.example.votingsystem.dto.response;

import java.time.LocalDateTime;

public record VoteSessionResultResponse(
    String title,
    String description,
    LocalDateTime start,
    LocalDateTime end,
    Boolean open,
    Long yesVotes,
    Long noVotes,
    Long nonVotes) {}
