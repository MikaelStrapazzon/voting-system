package com.example.votingsystem.util.dto;

public record ResponseMockMvcRequest<T>(int httpStatusCode, T data) {}
