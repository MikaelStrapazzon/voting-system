package com.example.votingsystem.exception.custom;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class EntityValidationException extends RuntimeException {
  private final Map<String, String> errors;

  public EntityValidationException(Map<String, String> errors) {
    this.errors = errors;
  }

  public EntityValidationException(String field, String error) {
    this.errors =
        new HashMap<>() {
          {
            put(field, error);
          }
        };
  }
}
