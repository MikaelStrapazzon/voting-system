package com.example.votingsystem.dto.response;

import java.time.LocalDateTime;

public record CreateUserResponse(Integer id, String name, String cpf, LocalDateTime createdAt) {}
