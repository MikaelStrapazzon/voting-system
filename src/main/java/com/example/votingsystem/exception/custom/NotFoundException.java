package com.example.votingsystem.exception.custom;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
  private final String error;

  public NotFoundException(String error) {
    this.error = error;
  }
}
