package com.example.votingsystem.dto.response.generic;

import java.util.List;

public record ErrorDetailsFieldResponse(String message, List<ErrorField> details) {}
